package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.rpc.IInterface;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

class VrpcClientAdapterTest {
    ConnectionFactory connectionFactory = new ConnectionFactory(new ConnectionOptions(),new HeartbeatTrigger(new RpcProtocolV1MessageFactory()));
    ConnectionManager manager = new ConnectionManager(connectionFactory,new RandomSelectStrategy());
    VrpcClientAdapter clientAdapter = new VrpcClientAdapter(new URLParser(),manager);

    String url = "127.0.0.1:9527?key1=val1&key2=val2";
    Random random = new Random();

    @Test
    public void syncInvoke() throws InterruptedException {
        while (true) {
            try {
                Object o = clientAdapter.syncInvoke(url, new InvokeContext(), new RpcRequest(IInterface.class.getName(), "dothing", new Class[]{String.class}, new Object[]{String.valueOf(random.nextInt())}));
                System.out.println(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Thread.sleep(3000);
        }
    }


    public void asyncInvoke(){

    }

    public void invokeWithCallBack(){

    }
}