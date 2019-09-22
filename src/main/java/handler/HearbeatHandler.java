package handler;
/**
 * Created by VLoye on 2019/9/16.
 */

import core.config.HearBeatConfig;
import core.message.HearbeatMessage;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.service.AbstractService;

/**
 * @author V
 * @Classname HearbeatHandler
 * @Description
 **/
public class HearbeatHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(HearbeatHandler.class);
    private HearBeatConfig config;

    public HearbeatHandler(HearBeatConfig config) {
        this.config = config;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof byte[]) {
            if (config != null && config.isUseful()) {
                byte[] msg = (byte[]) o;
                if (isMsg(msg)) {
                    ctx.writeAndFlush(config.getAck()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isSuccess()) {
                                logger.info("Send heartbeat message to client.");
                                return;
                            }
                            logger.warn("Fail to send heartbeat message to client, conn will be closed.");
                            ctx.close();
                            AbstractService.channelsMap.remove(ctx.channel().attr(AbstractService.ATTR_CHANNEL_KEY).get());
                        }
                    });
                }
            }
        }else {
            ctx.fireChannelRead(o);
        }
    }

    private boolean isMsg(byte[] bytes) {
        if (bytes.length != config.getMsg().length) {
            return false;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != config.getMsg()[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            logger.warn("The conn idle timeout and will be closed.");
            AbstractService.channelsMap.remove(ctx.channel().attr(AbstractService.ATTR_CHANNEL_KEY).get());
            ctx.channel().close();
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
