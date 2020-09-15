package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DuplexChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.List;

import static cn.v.vrpc.client.ConnectionFactory.KEY_CONNECTION_OPTIONS;

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        
        if (msg instanceof RpcMessageFrame) {
            if (ctx.channel().hasAttr(KEY_CONNECTION_OPTIONS) && ctx.channel().attr(KEY_CONNECTION_OPTIONS).get()
                .get(ConnectionOptions.OptionsKey.TYPE).equals(ConnectionOptions.OptionsKey.TYPE_SERVER)) {
                RpcMessageFrame frame = (RpcMessageFrame) msg;
                if (frame.getType() == 0xfe) {
                    heartbeatTrigger.responseHeartbeat(ctx);
                    return;
                }
            }
        }
        ctx.fireChannelRead(msg);
    }
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            heartbeatTrigger.triggerHeartbeat(ctx);
        }
        super.userEventTriggered(ctx, evt);
    }
}
