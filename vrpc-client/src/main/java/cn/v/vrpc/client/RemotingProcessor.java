package cn.v.vrpc.client;

import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.protocol.Transportable;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.Executor;

/**
 * v
 * 2020/1/12 上午11:29
 * 1.0
 */
public interface RemotingProcessor<T> {

    void process(InvokeContext invokeContext, T msg);

    Executor getExecutor();

    void setExecutor(Executor executor);

}
