package cn.v.vrpc.client;

import cn.v.vrpc.client.rpc.ServiceManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * v
 * 2020/1/13 上午12:21
 * 1.0
 */
public abstract class AbstractRemotingProcessor<T> implements RemotingProcessor<T> {
    protected Executor executor;


    public AbstractRemotingProcessor() {
        executor = Executors.newFixedThreadPool(1, new NamedThreadFactory("Vprc-pool"));
    }

    public AbstractRemotingProcessor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

}
