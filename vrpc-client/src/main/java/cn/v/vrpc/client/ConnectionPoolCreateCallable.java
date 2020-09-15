package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;

import java.util.concurrent.Callable;

/**
 * v
 * 2019/12/18 上午12:13
 * 1.0
 */
public class ConnectionPoolCreateCallable implements Callable<ConnectionPool> {
    protected URL url;
    protected ConnectionSelectStrategy selectStrategy;
    protected ConnectionFactory connectionFactory;
    protected ConnectionEventListener connectionEventListener;


    @Deprecated
    public ConnectionPoolCreateCallable(URL url, ConnectionSelectStrategy selectStrategy) {
        this(url, selectStrategy, new ConnectionFactory(new ConnectionOptions(), new HeartbeatTrigger(new RpcProtocolV1MessageFactory())));
    }

    public ConnectionPoolCreateCallable(URL url, ConnectionSelectStrategy selectStrategy, ConnectionFactory connectionFactory) {
        this(url,selectStrategy,connectionFactory,null);
    }

    public ConnectionPoolCreateCallable(URL url, ConnectionSelectStrategy selectStrategy, ConnectionFactory connectionFactory, ConnectionEventListener connectionEventListener) {
        this.url = url;
        this.selectStrategy = selectStrategy;
        this.connectionFactory = connectionFactory;
        this.connectionEventListener = connectionEventListener;
    }

    @Override
    public ConnectionPool call() throws Exception {
        ConnectionPool pool = new ConnectionPool(selectStrategy);
        pool.createConnection(connectionFactory.createConnection(url));
        return pool;
    }
}
