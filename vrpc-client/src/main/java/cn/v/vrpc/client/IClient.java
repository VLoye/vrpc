package cn.v.vrpc.client;

public interface IClient {
    Object syncInvoke(URL url, RequestMessage message);

    Object syncInvoke(URL url, RequestHeader header, RequestMessage body);

    Object syncInvoke(RequestHeader header, RequestMessage body);


//    ResponseFuture callWithFuture();

    void callWithCallback();




}
