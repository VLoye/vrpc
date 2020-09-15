package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.client.RemotingException;

import java.lang.reflect.Proxy;

/**
 * v
 * 2020/1/11 下午11:53
 * 1.0
 * 作为代理接口，用户关心：
 * 1、请求超时时间
 * 2、请求处理线程池
 * 3、远程服务地址
 *
 * 不关心：
 * 1、底层通讯方式及协议
 */
public class VrpcProxyFactory {
    public static <T> T getProxy(Class<?> clazz, String url, InvokeContext invokeContext) throws RemotingException {
        if(url == null){
            throw new RemotingException("url could not be null.");
        }
        if (invokeContext == null) {
            invokeContext = new InvokeContext();
        }
        return (T) Proxy.newProxyInstance(VrpcProxyFactory.class.getClassLoader(), new Class[]{clazz}, new VrpcInvocationHandler(clazz, url,invokeContext));
    }

    public static <T> T getProxy(Class<?> clazz, String url) throws RemotingException {
        return getProxy(clazz, url, null);
    }


}
