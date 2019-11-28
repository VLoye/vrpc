/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package client;

import client.core.ClientConfig;

import core.LifeCycleException;
import core.ServiceStatus;
import core.exc.ConnectionException;
import core.exc.RequestTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Predicate;

/**
 * @author V
 * @data 2019/9/23 18:59
 * @Description 连接管理器
 * 1、建立所有连接 v
 * 2、连接状态监控，重连
 **/
public class ConnManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnManager.class);
    public static ConcurrentHashMap<Integer, ResponseFuture> futuresMap = new ConcurrentHashMap<>();
    public LinkedBlockingQueue<IConnection> conns = new LinkedBlockingQueue<>();
    //重连次数、间隔
    public LinkedBlockingQueue<IConnection> failConns = new LinkedBlockingQueue<IConnection>();
    private StateChecker checker;
    private ClientConfig config;
    private ServiceStatus status = ServiceStatus.NEW;

    public ConnManager(ClientConfig config) {
        this.config = config;
    }

    public void init() {
        // TODO: 2019/9/24
    }

    public void start() {
        checker = new StateChecker("RPC-State-Checker");
        checker.start();
        List<String> addrs = config.getAddrs();
        for (String addr : addrs) {
            IConnection connection = null;
            try {
                connection = new DefaultConnection(addr, config, this);
                connection.start();
                conns.offer(connection);
            } catch (Exception e) {
                failConns.offer(connection);
                logger.warn("connect to {} failed.", addr);
            }
        }
        status = ServiceStatus.STAER;
    }


    public void recycle(IConnection conn) {
        conns.offer(conn);
    }


    public IConnection getConn() {
        IConnection connection = conns.poll();
        connection.setBusy(true);
        return connection;
    }

    public IConnection getConn(long timeout) throws RequestTimeoutException {
        IConnection connection = null;
        try {
            connection = conns.poll(timeout, TimeUnit.MILLISECONDS);
            if (connection == null) {
                throw new RequestTimeoutException("Get available connection timeout.");
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        connection.setBusy(true);
        return connection;
    }


    private class StateChecker extends Thread {
        private static final long INTERVAL = 10000;

        public StateChecker(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!status.equals(ServiceStatus.STOP)) {
                Iterator<IConnection> iterator = conns.iterator();
                while (iterator.hasNext()) {
                    IConnection connection = iterator.next();
                    if (!connection.isActive()) {
                        failConns.add(connection);
                        iterator.remove();
                    }
                }

                failConns.forEach(x -> {
                    try {
                        x.reconnect();
                        conns.offer(x);
                        failConns.remove(x);
                    } catch (Exception e) {
                        logger.warn("Reconnect to server {} fail, cause by: {}", x.getAddress(), e.getMessage());
                    }

                });
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException e) {
                    logger.error("Connection manager cause a exec: {}", e.getMessage());
                }
            }
        }
    }

}
