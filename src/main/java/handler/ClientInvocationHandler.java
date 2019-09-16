package handler;
/**
 * Created by VLoye on 2019/8/8.
 */

import core.IInvocation;
import core.RpcResult;
import message.RpcMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author V
 * @Classname ClientInvocationHandler
 * @Description
 **/
public class ClientInvocationHandler implements InvocationHandler {
    private IInvocation invocation;


    @Override
    public RpcResult invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcMessage message = new RpcMessage(method,args);
        return invocation.invoke(message);
    }
}
