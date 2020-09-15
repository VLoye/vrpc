package cn.v.vrpc.client;

import cn.v.vrpc.protocol.Transportable;
import io.netty.channel.Channel;

import java.util.concurrent.TimeoutException;

/**
 * v
 * 2019/12/16 下午9:51
 * 1.0
 */
public interface Connection {

    Object syncInvoke(Transportable msg) throws RemotingException, InterruptedException;
    Object syncInvoke(Transportable msg,long timeout) throws RemotingException, TimeoutException, InterruptedException;

    ResponseFuture asyncInvoke(Transportable msg) throws RemotingException;
    ResponseFuture asyncInvoke(Transportable msg, long time) throws RemotingException;


    URL URL();
    Channel getChannel();
    void setChannel(Channel channel);
    boolean isActive();

}
