package cn.v.vrpc.client;

import java.util.concurrent.Callable;

/**
 * v
 * 2019/12/18 上午12:13
 * 1.0
 */
public class ConnectionPoolCreateCallable implements Callable<ConnectionPool> {
    private URL url;

    public ConnectionPoolCreateCallable(URL url) {
    }

    @Override
    public ConnectionPool call() throws Exception {
        return null;
    }
}
