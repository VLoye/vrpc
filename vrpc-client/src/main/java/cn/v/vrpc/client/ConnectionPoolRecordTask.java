package cn.v.vrpc.client;


import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * v
 * 2019/12/17 下午11:53
 * 1.0
 */
public class ConnectionPoolRecordTask extends FutureTask<ConnectionPool> {
    private AtomicBoolean isRun = new AtomicBoolean(false);

    public ConnectionPoolRecordTask(Callable<ConnectionPool> callable) {
        super(callable);
    }

    @Override
    public void run() {
        isRun.set(true);
        super.run();
    }

}
