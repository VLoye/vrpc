package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.AbstractRemotingProcessor;
import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.client.ResponseFuture;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;

import java.util.concurrent.Executor;

/**
 * v
 * 2020/1/13 下午11:41
 * 1.0
 */
public class UserProcessor extends AbstractRemotingProcessor<RpcMessageFrame> {


    @Override
    public void process(InvokeContext invokeContext, RpcMessageFrame msg) {
        getExecutor().execute(new InnerTask(invokeContext, (RpcResponse) msg.getBody()));
    }


    class InnerTask implements Runnable {
        private InvokeContext context;
        private RpcResponse rpcResponse;

        public InnerTask(InvokeContext invokeContext, RpcResponse msg) {
            this.context = invokeContext;
            this.rpcResponse = msg;
        }

        @Override
        public void run() {
            ResponseFuture future = ResponseFuture.findResponseFuture(context.getSessionId());
            if (rpcResponse.isSuccess()) {
                future.success(rpcResponse.getResult());
            } else {
                future.fail(rpcResponse.getCause());
            }
        }
    }
}
