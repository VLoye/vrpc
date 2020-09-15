package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.AbstractRemotingProcessor;
import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.client.NamedThreadFactory;
import cn.v.vrpc.client.RpcRequest;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * v
 * 2020/1/13 上午12:21
 * 1.0
 */
public class VprcRemotingProcessor extends AbstractRemotingProcessor<RpcMessageFrame> {
    private ServiceManager serviceManager;


    public VprcRemotingProcessor(ServiceManager serviceManager) {
        super();
        this.serviceManager = serviceManager;
    }


    @Override
    public void process(InvokeContext invokeContext, RpcMessageFrame msg) {
        invokeContext.setServiceManager(serviceManager);
        RpcRequest rpcRequest = (RpcRequest) msg.getBody();
        getExecutor().execute(new InvokeTask(rpcRequest, invokeContext));
    }

}
