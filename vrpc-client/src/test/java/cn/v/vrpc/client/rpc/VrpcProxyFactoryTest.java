package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.RemotingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VrpcProxyFactoryTest {
    String url = "127.0.0.1:9527?ke1=val2&key2=val2";


    @Test
    void getProxy() throws RemotingException {
        IInterface aService = VrpcProxyFactory.getProxy(IInterface.class, url);
        String result = aService.dothing("do something");
    }
}