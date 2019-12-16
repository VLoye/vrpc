package cn.v.vrpc.client;

public interface IClient {
    Object syncInvoke(URL url, Object message);

    Object syncInvoke(URL url, InvokeContext context, Object body);

    Object syncInvoke(InvokeContext context, Object body);


    ResponseFuture callWithFuture(URL url, Object message);
    ResponseFuture callWithFuture(URL url, InvokeContext context, Object body);
    ResponseFuture callWithFuture(InvokeContext context, Object body);

    void callWithCallback(URL url, Object message);
    void callWithCallback(URL url, InvokeContext context, Object body);
    void callWithCallback(InvokeContext context, Object body);

    Connection getConn(URL url);

//    boolean isStartup();
}
