package cn.v.vrpc.client.test;

import cn.v.vrpc.client.HeartbeatTrigger;
import cn.v.vrpc.client.RpcProtocolV1MessageFactory;
import cn.v.vrpc.client.ServerConnectionFactory;
import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.rpc.AService;
import cn.v.vrpc.client.rpc.IInterface;

import java.util.concurrent.CountDownLatch;

/**
 * v
 * 2020/1/6 下午11:49
 * 1.0
 */
public class RpcServerTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        HeartbeatTrigger heartbeatTrigger = new HeartbeatTrigger(new RpcProtocolV1MessageFactory());
        ConnectionOptions options = new ConnectionOptions();
        ServerConnectionFactory factory = new ServerConnectionFactory(options, heartbeatTrigger);
        AService aService = new AService();
        factory.registerService(IInterface.class, aService);
        factory.start();

        latch.await();

    }
}
