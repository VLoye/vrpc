package cn.v.vrpc.client;


import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * v
 * 2019/12/16 下午10:13
 * 1.0
 * 选项：重连次数、重连间隔
 */
public class ReconnectionTrigger extends AbstractLifeCycle implements LifeCycle {
    //config
    private int maxTimes;
    private long interval;

    private HashMap<Connection, AtomicInteger> timeCache = new HashMap<>();

    private ScheduledFuture scheduledFuture;

    protected ConnectionManager connectionManager;
    protected ScheduledExecutorService scheduleService;     //for schedule
    //    protected ConnectionPool connectionPool;    //get all conns
    protected ConnectionFactory connectionFactory;  //reconnect

    public ReconnectionTrigger(int maxTimes, long interval, ConnectionManager connectionManager, ConnectionFactory connectionFactory) {
        this.maxTimes = maxTimes;
        this.interval = interval;
        this.connectionManager = connectionManager;
        this.connectionFactory = connectionFactory;
    }

    public ReconnectionTrigger(int maxTimes, long interval, ConnectionFactory connectionFactory) {
        this.maxTimes = maxTimes;
        this.interval = interval;
        this.connectionFactory = connectionFactory;
    }

    private class CheckTask implements Runnable {

        @Override
        public void run() {
            List<ConnectionPool> poolList = connectionManager.getPools();
            for (ConnectionPool connectionPool : poolList) {
                for (Connection connection : connectionPool.getAllConnections()) {
                    if (!connection.isActive()) {
                        if (timeCache.containsKey(connection) && timeCache.get(connection).get() >= maxTimes) {
                            break;
                        }
                        boolean success = connectionFactory.reconnect(connection);
                        if (!success) {
                            if (!timeCache.containsKey(connection)) {
                                timeCache.put(connection, new AtomicInteger(0));
                            }
                            timeCache.get(connection).getAndIncrement();
                        }
                    }
                }
            }
        }
    }


    @Override
    public void initialize() throws LifeCycleException {
        scheduleService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("Reconnect-Thread-", true));
    }

    @Override
    public void startup() throws LifeCycleException {
        initialize();
        scheduledFuture = scheduleService.scheduleAtFixedRate(new CheckTask(), 1000, interval, TimeUnit.MILLISECONDS);
    }

    @Override
    public void shutdown() throws LifeCycleException {
        scheduledFuture.cancel(false);
    }
}
