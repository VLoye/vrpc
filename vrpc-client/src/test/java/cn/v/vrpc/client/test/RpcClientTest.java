package cn.v.vrpc.client.test;

import cn.v.vrpc.client.*;
import cn.v.vrpc.client.config.ConnectionOptions;

import java.util.concurrent.CountDownLatch;

/**
 * v
 * 2020/1/6 下午11:49
 * 1.0
 */
public class RpcClientTest {
    public static void main(String[] args) throws InterruptedException, RemotingException {
        ConnectionOptions options = new ConnectionOptions();
        VrpcClientAdapter adapter = new VrpcClientAdapter();
        Object o = adapter.syncInvoke("127.0.0.1:9527?key1=val1&key2=val2","xxx");

    }


}
