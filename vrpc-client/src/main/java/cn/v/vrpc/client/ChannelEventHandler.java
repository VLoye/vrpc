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
 *
 * 连接事件处理器
 *
 * 针对 inactive 事件进行重连操作
 * 可在这类处理 自定义用户事件
 * 可在事件连接成功后初始化某些动作
 *
 */
public class ChannelEventHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChannelEventHandler.class);

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] register", ctx.channel().id().asShortText());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] unregister", ctx.channel().id().asShortText());
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] active", ctx.channel().id().asShortText());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] inactive", ctx.channel().id().asShortText());
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.debug("channel[id ={}] user event trigger", ctx.channel().id().asShortText());
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("channel[id ={}] cause a exception: {}", ctx.channel().id().asShortText(), cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
