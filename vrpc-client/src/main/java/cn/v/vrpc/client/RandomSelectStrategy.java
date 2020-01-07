package cn.v.vrpc.client;

import java.util.List;
import java.util.Random;

/**
 * v
 * 2020/1/7 下午11:19
 * 1.0
 */
public class RandomSelectStrategy implements ConnectionSelectStrategy {
    private Random r = new Random();
    private int retryTime = 5;

    @Override
    public Connection select(List<Connection> connections) {
        Connection connection = null;
        int i = 0;
        while (connection == null && i < retryTime) {
            Connection conn = connections.get(r.nextInt(connections.size()));
            if (conn.isActive())
                connection = conn;
        }
        return connection;
    }
}
