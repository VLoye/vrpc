package cn.v.vrpc.client;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * v
 * 2019/12/16 下午10:14
 * 1.0
 */
public abstract class AbstractLifeCycle implements LifeCycle {
    protected AtomicBoolean isStartup;


    @Override
    public void startup() throws LifeCycleException {

    }

    @Override
    public void shutdown() throws LifeCycleException {

    }
}
