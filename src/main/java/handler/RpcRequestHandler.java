package handler;
/**
 * Created by VLoye on 2019/8/17.
 */

import core.InvocationTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import core.message.RpcMessage;
import core.message.RpcRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.RpcServer;
import server.service.AbstractService;


/**
 * 调用模式，同步或异步？
 * 同步：同步阻塞调用返回
 * 异步：异步返回future，添加监听器回调
 *
 * @author V
 * @Classname RpcRequestHandler
 * @Description
 **/
public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcMessage> {
    private static final Logger logger = LoggerFactory.getLogger(RpcRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcMessage message) throws Exception {
        RpcRequestMessage msg = (RpcRequestMessage) message;
        message.setSessionId(channelHandlerContext.channel().attr(AbstractService.ATTR_CHANNEL_KEY).get());
        RpcServer.executor.execute(new InvocationTask(msg));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
