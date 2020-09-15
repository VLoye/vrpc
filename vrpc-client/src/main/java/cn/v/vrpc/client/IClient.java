package cn.v.vrpc.client;

import cn.v.vrpc.protocol.Transportable;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;

public interface IClient {
    @Deprecated
    Object syncInvoke(String url, Object message) throws RemotingException,InterruptedException;

    Object syncInvoke(String url, InvokeContext context, Object body) throws RemotingException, InterruptedException, TimeoutException;

    Object syncInvoke(InvokeContext context, Object body);

    ResponseFuture callWithFuture(String url, Object message) throws RemotingException;
    ResponseFuture callWithFuture(String url, InvokeContext context, Object body) throws RemotingException;
    ResponseFuture callWithFuture(InvokeContext context, Object body);

    // 被监听器替代
//    void callWithCallback(String url, Object message);
//    void callWithCallback(String url, InvokeContext context, Object body);
//    void callWithCallback(InvokeContext context, Object body);

    Connection getConn(String url) throws RemotingException;

//    boolean isStartup();
}
