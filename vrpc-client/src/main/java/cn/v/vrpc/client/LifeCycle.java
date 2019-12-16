package cn.v.vrpc.client;

public interface LifeCycle {
    void startup() throws LifeCycleException;
    void shutdown() throws LifeCycleException;
}
