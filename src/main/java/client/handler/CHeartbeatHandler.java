package client.handler;
/**
 * Created by V on 2019/9/22.
 */

import client.core.ClientConfig;
import core.config.HearBeatConfig;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname CHeartbeatHandler
 * @Description
 **/
public class CHeartbeatHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(CHeartbeatHandler.class);
    private ClientConfig config;

    public CHeartbeatHandler(ClientConfig config) {
        this.config = config;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (!config.isUseHeartbeat()) {
                super.userEventTriggered(ctx, evt);
                return;
            }
            ctx.writeAndFlush(config.getMsg()).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        logger.info("Clien send heartbeat message to server");
                        return;
                    }
                    logger.info("Heartbeat message can't send to server, conn will be closed");
                    ctx.close();
                }
            });

        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!config.isUseHeartbeat()){
            ctx.fireChannelRead(msg);
            return;
        }
        if(msg instanceof byte[]){
            byte[] ack = (byte[])msg;
            if(isAck(ack)){
                logger.info("receive heartbeat response from server.");
                return;
            }
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private boolean isAck(byte[] bytes) {
        if (bytes.length != config.getAck().length) {
            return false;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != config.getAck()[i]) {
                return false;
            }
        }
        return true;
    }
}
