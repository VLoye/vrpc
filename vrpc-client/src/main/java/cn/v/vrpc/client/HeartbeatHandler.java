package cn.v.vrpc.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DuplexChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.List;

/**
 * v
 * 2019/12/28 上午12:32
 * 1.0
 */
public class HeartbeatHandler extends ChannelDuplexHandler {
    private HeartbeatTrigger heartbeatTrigger;
    public HeartbeatHandler(HeartbeatTrigger heartbeatTrigger) {
        this.heartbeatTrigger = heartbeatTrigger;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            heartbeatTrigger.triggerHeartbeat(ctx);
        }
        super.userEventTriggered(ctx, evt);
    }
}
