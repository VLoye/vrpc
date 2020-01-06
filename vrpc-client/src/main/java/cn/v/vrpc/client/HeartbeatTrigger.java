package cn.v.vrpc.client;

import io.netty.channel.ChannelHandlerContext;

/**
 * v
 * 2019/12/16 下午10:13
 * 1.0
 * 心跳触发器
 * 两种心跳机制
 * 1、由报文协议某个标志位来确定该报文属于心跳报文。
 * 只需要发送、接收。
 * 2、由自定义的报文体来充当心跳报文。
 * 需要校验报文信息、发送、接收。
 * <p>
 * 由于使用自定义协议，这里选择第一种。可由协议码从工厂中获取触发器？
 * <p>
 * 设计前的思考：
 * 不同协议 对应 不同的心跳报文?还是由协议某个标志位来进行报文的
 * 如何设计心跳：由消息工厂生成心跳信息？
 * 只是触发，那么是否需要响应？
 * 服务端在哪里进行解码时进行区分，那么如果获取到该对象，如何检验报文，
 */
public class HeartbeatTrigger {
    //这里应该是消息工厂。
    private MessageFactory messageFactory;

    public HeartbeatTrigger(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    public void triggerHeartbeat(ChannelHandlerContext context) {
        context.writeAndFlush(messageFactory.ping());
    }

    public void responseHeartbeat(ChannelHandlerContext context) {
        context.writeAndFlush(messageFactory.pong());
    }

}
