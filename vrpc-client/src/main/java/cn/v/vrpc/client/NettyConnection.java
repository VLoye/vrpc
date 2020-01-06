package cn.v.vrpc.client;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * v
 * 2019/12/20 上午12:38
 * 1.0
 */
public class NettyConnection implements Connection {
    private Channel channel;

    private long executeTimes;
    private long maxExecuteTime;
    private long minExecuteTime;
    // 平均
    private long averageTimes;


    public NettyConnection(Channel channel) {
        this.channel = channel;
    }


    public Object syncInvoke(Object msg) throws RemotingException {
        if (msg == null) {
            return null;
        }
        ChannelFuture future = channel.writeAndFlush(msg);

        try {
            Object response = future.get();
            return response;
        } catch (InterruptedException | ExecutionException e) {
            throw new RemotingException(e);
        }
    }

    @Override
    public Object syncInvoke(Object msg, long timeout) throws RemotingException, TimeoutException {
        if (msg == null) {
            return null;
        }
        ChannelFuture future = channel.writeAndFlush(msg);

        try {
            Object response = future.get(timeout, TimeUnit.MILLISECONDS);
            return response;
        } catch (InterruptedException | ExecutionException e) {
            throw new RemotingException(e);
        }
    }

    public ResponseFuture asyncInvoke(Object msg) throws RemotingException {
        ResponseFuture responseFuture = new ResponseFuture();

        return responseFuture;
    }

}
