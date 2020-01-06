package cn.v.vrpc.client;

import java.rmi.RemoteException;

public interface IClient {
    Object syncInvoke(String url, Object message) throws RemotingException;

    Object syncInvoke(String url, InvokeContext context, Object body);

    Object syncInvoke(InvokeContext context, Object body);


    ResponseFuture callWithFuture(String url, Object message);
    ResponseFuture callWithFuture(String url, InvokeContext context, Object body);
    ResponseFuture callWithFuture(InvokeContext context, Object body);

    void callWithCallback(String url, Object message);
    void callWithCallback(String url, InvokeContext context, Object body);
    void callWithCallback(InvokeContext context, Object body);

    Connection getConn(String url) throws RemotingException;

//    boolean isStartup();
}
