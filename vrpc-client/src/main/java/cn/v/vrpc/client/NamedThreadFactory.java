package cn.v.vrpc.client;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * v
 * 2020/1/13 下午10:58
 * 1.0
 */
public class NamedThreadFactory implements ThreadFactory {
    private final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String prefix;
    private boolean daemon = false;
    private ThreadGroup group;
    private String namePrefix;

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = prefix + "-" + poolNumber.getAndIncrement() + "-";
        this.prefix = prefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
    }
}
