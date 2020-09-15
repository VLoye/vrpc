package cn.v.vrpc.client;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * v
 * 2019/12/28 下午1:26
 * 1.0
 * <p>
 * 连接事件处理器
 * <p>
 * 针对 inactive 事件进行重连操作
 * 可在这类处理 自定义用户事件
 * 可在事件连接成功后初始化某些动作
 */
public class ChannelEventHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChannelEventHandler.class);
    private ConnectionEventListener connectionEventListener;

    public ChannelEventHandler(ConnectionEventListener connectionEventListener) {
        this.connectionEventListener = connectionEventListener;
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        connectionEventListener.close(ctx, promise);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        connectionEventListener.channelRegistered(ctx);
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        connectionEventListener.channelUnregistered(ctx);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        connectionEventListener.channelActive(ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        connectionEventListener.channelInactive(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        connectionEventListener.userEventTriggered(ctx, evt);
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        connectionEventListener.exceptionCaught(ctx, cause);
        super.exceptionCaught(ctx, cause);
    }
}
