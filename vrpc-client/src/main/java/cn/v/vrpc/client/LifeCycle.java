package cn.v.vrpc.client;

public interface LifeCycle {
    void initialize() throws LifeCycleException;
    void startup() throws LifeCycleException;
    void shutdown() throws LifeCycleException;
}
