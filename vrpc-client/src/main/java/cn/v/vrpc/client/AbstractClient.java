package cn.v.vrpc.client;

import jdk.nashorn.internal.runtime.linker.Bootstrap;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Implement for Remote transport client
 * v
 * 2019/12/16 下午9:53
 * 1.0
 */
public abstract class AbstractClient extends AbstractLifeCycle implements IClient {
    protected URLParser urlParser;
    protected ConnectionManager connectionManager;
    protected ConnectionFactory connectionFactory;
    protected ConnectionSelectStrategy connectionSelectStrategy;
    protected ReconnectionTrigger reconnectionTrigger;

    protected HeartbeatTrigger heartbeatTrigger;

    protected ConnectionEventListener connectionEventListener;

    protected Map<CommandCode, ExecutorService> userProcessors;


    public AbstractClient() {
        this.urlParser = new URLParser();
        this.connectionManager = new ConnectionManager();

    }


    protected boolean isStartup() {
        return isStartup.get() == true;
    }

    @Override
    public Object syncInvoke(String url, Object message) throws RemotingException {
        Connection connection = getConn(url);
        if (null == connection) {
            throw new RemotingException("Has not usable connection.");
        }
        return connection.syncInvoke(message);
    }

    @Override
    public Object syncInvoke(String url, InvokeContext context, Object body) {
        return null;
    }

    @Override
    public Object syncInvoke(InvokeContext context, Object body) {
        return null;
    }

    @Override
    public ResponseFuture callWithFuture(String url, Object message) {
        return null;
    }

    @Override
    public ResponseFuture callWithFuture(String url, InvokeContext context, Object body) {
        return null;
    }

    @Override
    public ResponseFuture callWithFuture(InvokeContext context, Object body) {
        return null;
    }

//    @Override
//    public void callWithCallback(URL url, Object message) {
//
//    }
//
//    @Override
//    public void callWithCallback(URL url, InvokeContext context, Object body) {
//
//    }
//
//    @Override
//    public void callWithCallback(InvokeContext context, Object body) {
//
//    }

    @Override
    public Connection getConn(String url) throws RemotingException {
        URL u = urlParser.parse(url);
        ConnectionPool pool = connectionManager.getConnectionPoolAndPutIfAbsent(u.getPoolKey());
        return pool.get();
    }

}
