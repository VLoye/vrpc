package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * v
 * 2020/1/16 上午1:15
 * 1.0
 */
public class DefaultConnectionEventListener implements ConnectionEventListener {
    private static final Logger logger = LoggerFactory.getLogger(DefaultConnectionEventListener.class);

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.debug("channel[id ={}] close", ctx.channel().id().asShortText());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] register", ctx.channel().id().asShortText());

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] unregister", ctx.channel().id().asShortText());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] active", ctx.channel().id().asShortText());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("channel[id ={}] inactive", ctx.channel().id().asShortText());

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.debug("channel[id ={}] user event trigger", ctx.channel().id().asShortText());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("channel[id ={}] cause a exception: {}", ctx.channel().id().asShortText(), cause.getMessage());

    }
}
