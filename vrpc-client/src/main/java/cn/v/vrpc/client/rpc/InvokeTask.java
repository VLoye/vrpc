package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.client.RpcRequest;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * v
 * 2020/1/13 上午12:51
 * 1.0
 */
public class InvokeTask implements Runnable {
    private RpcRequest message;
    private InvokeContext context;

    public InvokeTask(RpcRequest message, InvokeContext context) {
        this.message = message;
        this.context = context;
    }

    public InvokeTask(InvokeContext invokeContext, RpcMessageFrame msg) {
    }

    @Override
    public void run() {
        RpcResponse rpcResponse =new RpcResponse();
        try {
            Object target = context.getServiceManager().getTarget(message.getClassName());
            Class<?> c = this.getClass().getClassLoader().loadClass(message.getClassName());
            Method method = c.getMethod(message.getMethod(),message.getParamTypes());
            method.setAccessible(true);
            Object result = method.invoke(target,message.getArgs());
            rpcResponse.setSuccess(true);
            rpcResponse.setResult(result);
        } catch (Exception e) {
            rpcResponse.setCause(e);
        }

        RpcMessageFrame frame = createRemotingMessage(rpcResponse);
        context.getCtx().channel().writeAndFlush(frame);

    }

    private RpcMessageFrame createRemotingMessage(RpcResponse rpcResponse) {
        RpcMessageFrame frame = RpcMessageFrame.RSP();
        frame.setId(context.getSessionId());
        frame.setBody(rpcResponse);
        return frame;
    }
}
