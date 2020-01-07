package cn.v.vrpc.client;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * v
 * 2019/12/17 下午10:55
 * 1.0
 */
public class ConnectionPool {


    private CopyOnWriteArrayList connections;
    private ConnectionSelectStrategy selectStrategy;

    public ConnectionPool(ConnectionSelectStrategy selectStrategy) {
        connections = new CopyOnWriteArrayList();
        this.selectStrategy = selectStrategy;
    }

    public Connection get() {
        if (null == connections || connections.size() == 0) {
            return null;
        }
        ArrayList list = new ArrayList(connections);
        return selectStrategy.select(list);
    }

    public void createConnection(Connection connection){
        connections.add(connection);
    }


}
