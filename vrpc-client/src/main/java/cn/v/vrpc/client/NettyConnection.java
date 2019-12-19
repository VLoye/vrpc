package cn.v.vrpc.client;


import io.netty.channel.Channel;

/**
 * v
 * 2019/12/20 上午12:38
 * 1.0
 */
public class NettyConnection implements Connection{
    private Channel channel;


    public NettyConnection(Channel channel) {
        this.channel = channel;
    }
}
