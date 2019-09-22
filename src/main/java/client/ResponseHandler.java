package client;
/**
 * Created by VLoye on 2019/9/4.
 */

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
        ResponseFuture future= RpcClient.futuresMap.get(msg.getId());
        future.setSuccess(msg.getResult());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        super.exceptionCaught(ctx, cause);
    }
}
