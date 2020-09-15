package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public class ConnectionManager implements LifeCycle {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    
    private Map<String, ConnectionPoolRecordTask> connectionPoolTasks = new ConcurrentHashMap<String, ConnectionPoolRecordTask>();
    
    protected ConnectionFactory connectionFactory;
    protected ConnectionSelectStrategy selectStrategy;
    protected ReconnectionTrigger reconnectionTrigger;
    
    //    private ReconnectionTrigger reconnectionTrigger;
    
    @Deprecated
    public ConnectionManager(ConnectionSelectStrategy selectStrategy) {
        this.selectStrategy = selectStrategy;
    }
    
    public ConnectionManager(ConnectionFactory connectionFactory, ConnectionSelectStrategy selectStrategy) {
        //        this(connectionFactory,selectStrategy,null);
        this(connectionFactory, selectStrategy, new ReconnectionTrigger(10, 5000, connectionFactory));
    }
    
    public ConnectionManager(ConnectionFactory connectionFactory, ConnectionSelectStrategy selectStrategy,
        ReconnectionTrigger reconnectionTrigger) {
        this.connectionFactory = connectionFactory;
        this.selectStrategy = selectStrategy;
        this.reconnectionTrigger = reconnectionTrigger;
    }
    
    public ConnectionPool getConnectionPoolAndPutIfAbsent(URL url) throws RemotingException {
        String poolKey = url.getPoolKey();
        ConnectionPoolRecordTask recordTask = null;
        ConnectionPoolCreateCallable createCallable = null;
        ConnectionPool pool = null;
        
        if (!connectionPoolTasks.containsKey(poolKey)) {
            createCallable = new ConnectionPoolCreateCallable(url, selectStrategy,
                new ConnectionFactory(ConnectionOptions.DEFAULT(),
                    new HeartbeatTrigger(new RpcProtocolV1MessageFactory()), new DefaultConnectionEventListener()));
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
    
    public List<ConnectionPool> getPools() {
        return connectionPoolTasks.values().stream().map(x -> {
            try {
                return x.get();
            } catch (Exception e) {
                logger.error(e.getMessage());
                return null;
            }
        }).collect(Collectors.toList());
    }
    
    @Override
    public void initialize() throws LifeCycleException {
    
    }
    
    @Override
    public void startup() throws LifeCycleException {
        if (reconnectionTrigger != null) {
            reconnectionTrigger.startup();
        }
    }
    
    @Override
    public void shutdown() throws LifeCycleException {
    
    }
    
}
