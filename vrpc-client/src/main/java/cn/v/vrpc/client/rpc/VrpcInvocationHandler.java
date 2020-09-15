package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.client.RpcRequest;
import cn.v.vrpc.client.VrpcClientAdapter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * v
 * 2020/1/11 下午11:58
 * 1.0
 */
public class VrpcInvocationHandler implements InvocationHandler {
    private VrpcClientAdapter clientAdapter;
    private Class<?> clazz;
    private String url;
    private InvokeContext invokeContext;
    
    public VrpcInvocationHandler(Class<?> clazz, String url, InvokeContext invokeContext) {
        clientAdapter = new VrpcClientAdapter();
        this.clazz = clazz;
        this.url = url;
        this.invokeContext = invokeContext;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(clientAdapter, args);
        }
        RpcRequest message = new RpcRequest();
        message.setClassName(clazz.getName());
        message.setMethod(method.getName());
        message.setParamTypes(
            Arrays.stream(args).map(x -> x.getClass()).collect(Collectors.toList()).toArray(new Class[0]));
        message.setArgs(args);
        return clientAdapter.syncInvoke(url, invokeContext, message);
    }
}
