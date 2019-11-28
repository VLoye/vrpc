package client.handler;
/**
 * Created by VLoye on 2019/9/4.
 */

import client.AbstractConnection;
import client.ConnManager;
import client.ResponseFuture;
import client.RpcClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import core.message.RpcResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ResponseHandler
 * @Description
 **/
public class ResponseHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage msg) throws Exception {
        ResponseFuture future= ConnManager.futuresMap.get(msg.getId());
        future.setSuccess(msg.getResult());
    }




    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.warn("Channel[{}] is inactive.",ctx.channel().id().asShortText());
//        ConnManager manager = (ConnManager)ctx.channel().attr(AbstractConnection.KEY_MANAGER).get();
        // TODO: 2019/11/28 ¶Ï¿ª´¦Àí 
        //        manager.failConns.offer(this);
//        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
