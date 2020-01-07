package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;

import java.util.concurrent.Callable;

/**
 * v
 * 2019/12/18 上午12:13
 * 1.0
 */
public class ConnectionPoolCreateCallable implements Callable<ConnectionPool> {
    private URL url;
    private ConnectionFactory connectionFactory;
    private ConnectionSelectStrategy selectStrategy;

    public ConnectionPoolCreateCallable(URL url,ConnectionSelectStrategy selectStrategy) {
        this.url = url;
        this.selectStrategy = selectStrategy;
        this.connectionFactory = new ConnectionFactory(new ConnectionOptions(),new HeartbeatTrigger(new RpcProtocolV1MessageFactory()));
    }

    @Override
    public ConnectionPool call() throws Exception {
        ConnectionPool pool = new ConnectionPool(selectStrategy);
        pool.createConnection(connectionFactory.createConnection(url));
        return pool;
    }
}
