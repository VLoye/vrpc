package cn.v.vrpc.client.test;

import cn.v.vrpc.client.*;
import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.rpc.IInterface;
import cn.v.vrpc.client.rpc.VrpcProxyFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

/**
 * v
 * 2020/1/6 下午11:49
 * 1.0
 */
public class RpcClientTest {
    public static void main(String[] args) throws InterruptedException, RemotingException, TimeoutException {
//        ConnectionOptions options = new ConnectionOptions();
//        VrpcClientAdapter adapter = new VrpcClientAdapter();
//        RpcProtocolV1MessageFactory factory = new RpcProtocolV1MessageFactory();
        InvokeContext context = new InvokeContext();
        context.setTimeout(10000L);
//        Object o = adapter.syncInvoke("127.0.0.1:9527?key1=val1&key2=val2", context, factory.request());

        String url = "127.0.0.1:9527?key1=val1&key2=val2";
        VrpcProxyFactory proxyFactory = new VrpcProxyFactory();
        IInterface proxy = VrpcProxyFactory.getProxy(IInterface.class, url, context);
        System.out.println(proxy.dothing("aaaaaaaa"));
    }


}
