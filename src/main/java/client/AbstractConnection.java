/*
 * Copyright(C) 2013 Agree Corporation. All rights reserved.
 * 
 * Contributors:
 *     Agree Corporation - initial API and implementation
 */
package client;

import client.core.ClientConfig;
import client.handler.CHeartbeatHandler;
import client.handler.ResponseHandler;
import codec.serialize.DeserializeHandler;
import codec.serialize.JDKSerialize;
import codec.serialize.SerializeHandler;
import common.RegexUtil;
import core.ServiceStatus;
import core.exc.AddressException;
import core.exc.ConnectionException;
import core.message.RpcRequestMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.service.AbstractService;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author V
 * @data 2019/9/23 19:30
 * @Description
 **/
public abstract class AbstractConnection implements IConnection {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConnection.class);

    protected String addr = "127.0.0.1:9999";
    protected String host = "127.0.0.1";
    protected int port = 9999;
    protected ServiceStatus status = ServiceStatus.NEW;
    protected ConnManager manager;
    protected boolean isOpen;
    protected ClientConfig config;

    public static final AttributeKey KEY_MANAGER = AttributeKey.newInstance("key.manager");

    protected volatile boolean isBusy;
    protected Bootstrap bootstrap;
    protected NioEventLoopGroup group;
    protected Channel channel;

    public AbstractConnection(String addr, ClientConfig config, ConnManager manager) {
        this.addr = addr;
        this.config = config;
        this.manager = manager;
    }

    private void resolveAddress() throws AddressException {
        int index = this.addr.indexOf(":");
        if (index < 0) {
            throw new AddressException("Address format is error.");
        }
        this.host = addr.substring(0, index);
        String port = addr.substring(index + 1);
        if (!RegexUtil.isNumberString(port)) {
            throw new AddressException("Address format is error.");
        }
        this.port = Integer.valueOf(port);
    }

    public void init() throws ConnectionException {
        resolveAddress();
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup(2);
        bootstrap.group(group)
                .attr(KEY_MANAGER, manager)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(2048, Unpooled.copiedBuffer(AbstractService.splitDelimiter)));
                        pipeline.addLast(new DeserializeHandler(new JDKSerialize()));
                        pipeline.addLast(new SerializeHandler(new JDKSerialize()));
                        pipeline.addLast(new IdleStateHandler(0, 0, 30));
                        pipeline.addLast(new CHeartbeatHandler(config));
                        pipeline.addLast(new ResponseHandler());
                    }
                });
    }

    public void start() throws ConnectionException {
        if (status == ServiceStatus.NEW) {
            init();
        }
        ChannelFuture future = null;
        try {
            channel = bootstrap.connect(new InetSocketAddress(host, port)).sync().channel();
            isOpen = true;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        logger.info("connect to rpc server[{}:{}] success, channelId is {}", host, port, channel.id().asShortText());
    }

    @Override
    public boolean isBusy() {
        return isBusy;
    }

    @Override
    public boolean setBusy(boolean busy) {
        return false;
    }

    @Override
    public ResponseFuture call(RpcRequestMessage message) {
        ResponseFuture future = new ResponseFuture(message.getSessionId());
        manager.futuresMap.put(message.getId(), future);
        try {
            channel.writeAndFlush(message);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        return future;
    }

    @Override
    public void release() {
        manager.recycle(this);
        this.isBusy = false;
    }

    @Override
    public void connect() throws ConnectionException {
        try {
            channel = bootstrap.connect(new InetSocketAddress(host, port)).sync().channel();
            isOpen = true;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        logger.info("connect to server[{}:{}] success", host, port);
    }

    @Override
    public void close() {
        // TODO: 2019/9/24
    }

    @Override
    public void reconnect() {
        try {
            channel = bootstrap.connect(new InetSocketAddress(host, port)).sync().channel();
            isOpen = true;
            logger.info("Reconnect to server {} success", addr);
        } catch (InterruptedException e) {
            logger.error("Connection Exception, reconnect to server {} failed, cause by: {}", addr, e.getMessage());
        }
    }

    @Override
    public boolean isActive() {
        return isOpen && channel.isActive();
    }

    @Override
    public ClientConfig getConfig() {
        return config;
    }

    @Override
    public String getAddress() {
        return addr;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }
}
