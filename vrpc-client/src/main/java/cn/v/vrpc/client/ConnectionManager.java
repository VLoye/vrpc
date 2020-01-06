package cn.v.vrpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private Map<String, ConnectionPoolRecordTask> connectionPoolTasks = new ConcurrentHashMap<String, ConnectionPoolRecordTask>();

    private URL url;
    private ReconnectionTrigger reconnectionTrigger;

    public ConnectionManager() {
    }

    public ConnectionPool getConnectionPoolAndPutIfAbsent(String poolKey) throws RemotingException {
        ConnectionPoolRecordTask recordTask = null;
        ConnectionPoolCreateCallable createCallable = null;
        ConnectionPool pool = null;

        if (!connectionPoolTasks.containsKey(poolKey)) {
            createCallable = new ConnectionPoolCreateCallable(url);
            recordTask = new ConnectionPoolRecordTask(createCallable);
            ConnectionPoolRecordTask tmp = connectionPoolTasks.putIfAbsent(poolKey, recordTask);
            if (tmp == null) {
                recordTask.run();
            }
        }

        try {
            pool = connectionPoolTasks.get(poolKey).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RemotingException(e);
        }
        if (pool == null) {
            throw new RemotingException("Has not usable connection pool.");
            // exception record
        }
        return pool;
    }
}
