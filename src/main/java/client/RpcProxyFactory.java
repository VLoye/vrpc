package client;/**
 * Created by V on 2019/11/29.
 */

import client.core.ClientConfig;
import core.LifeCycleException;
import core.exc.RpcException;

import java.lang.reflect.Proxy;

/**
 * V
 * 2019/11/29 10:19
 */
public class RpcProxyFactory {

    public static <T> T proxy(Class<T> clazz) throws RpcException {
        return getProxy(clazz,null);
    }

    public static <T> T proxy(Class<T> clazz, ClientConfig config) throws RpcException {
        return getProxy(clazz,config);
    }

    private static <T> T getProxy(Class<T> clazz, ClientConfig config) throws RpcException {
        ClientConfig clientConfig = config;
        if(clientConfig == null){
            clientConfig = ClientConfig.def().build();
        }
        RpcClient client = new RpcClient(clientConfig);
        try {
            client.start();
        } catch (LifeCycleException e) {
            throw new RpcException(e.getMessage());
        }
        T t = (T) Proxy.newProxyInstance(RpcProxyFactory.class.getClassLoader(), new Class[]{clazz}, new ClientProxyInvocationHandler(client));
        return t;
    }

}
