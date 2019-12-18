package cn.v.vrpc.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public class ConnectionManager {

    private Map<String,ConnectionPoolRecordTask> connectionPoolTasks = new ConcurrentHashMap<String,ConnectionPoolRecordTask>();


    private URL url;
//    private URLParser urlParser;
    private ConnectionManager connectionManager;
    private ReconnectionTrigger reconnectionTrigger;



    private ConnectionPool getConnectionPoolAndPutIfAbsent(String poolKey) throws  Exception{
        ConnectionPoolRecordTask recordTask = null;
        ConnectionPoolCreateCallable createCallable = null;
        ConnectionPool pool =null;

        if (!connectionPoolTasks.containsKey(poolKey)) {
            createCallable = new ConnectionPoolCreateCallable(url);
            recordTask = new ConnectionPoolRecordTask(createCallable);
            ConnectionPoolRecordTask tmp = connectionPoolTasks.putIfAbsent(poolKey,recordTask);
            if(tmp == recordTask){
                recordTask.run();
            }
        }

        pool = connectionPoolTasks.get(poolKey).get();
        if(pool == null){
            // exception record
        }
        return pool;
    }
}
