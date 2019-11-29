package client;/**
 * Created by V on 2019/11/29.
 */

import common.RpcUtil;
import core.message.RpcMessage;
import core.message.RpcRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * V
 * 2019/11/29 10:36
 */
public class ClientProxyInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientProxyInvocationHandler.class);
    private RpcClient client;

    public ClientProxyInvocationHandler(RpcClient client) {
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(client, args);
        }
        switch (methodName) {
            case "toString":
                return client.toString();
            case "equal":
                return client.equals(args[0]);
            case "hashCode":
                return client.hashCode();
            default: {
                Class c = client.getConfig().getService();
                RpcRequestMessage request = new RpcRequestMessage();
                request.setClassName(c.getName());
                request.setMethod(methodName);
                request.setParamTypes(resolveParameterTypes(args));
                request.setArgs(args);
                request.setReply(true);
                request.setSessionId(RpcUtil.UUID());
//                request.setType(0x01);
                ResponseFuture future = client.call(request, client.getConfig().getRequestTimeout());
                return future.get(client.getConfig().getRequestTimeout(), TimeUnit.MILLISECONDS);
            }
        }
//        return null;
    }

    private Class<?>[] resolveParameterTypes(Object[] args) {
        return Arrays.stream(args).map(x -> x.getClass()).collect(Collectors.toList()).toArray(new Class<?>[0]);
    }
}
