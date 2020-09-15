package cn.v.vrpc.client;

import cn.v.vrpc.client.rpc.VprcRemotingProcessor;
import cn.v.vrpc.protocol.ProtocolCode;
import cn.v.vrpc.protocol.Transportable;
import cn.v.vrpc.protocol.rpc.RpcComstant;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * v
 * 2020/1/11 上午12:55
 * 1.0
 */
public class MessageAsyncHandler extends SimpleChannelInboundHandler<Transportable> {
    private static final Logger logger = LoggerFactory.getLogger(MessageAsyncHandler.class);
    private RemotingProcessor processor;

    public MessageAsyncHandler(RemotingProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Transportable msg) throws Exception {
        if(msg instanceof RpcMessageFrame) {
            RpcMessageFrame rpcMessageFrame = (RpcMessageFrame) msg;
            logger.info(msg.toString());
            InvokeContext context = initInvokeContext(ctx, rpcMessageFrame);
            processor.process(context, (RpcMessageFrame) rpcMessageFrame);
        }else {
            logger.warn("unknown msg type: ",msg.getClass().toString());
        }
    }



    private InvokeContext initInvokeContext(ChannelHandlerContext ctx, RpcMessageFrame msg) {
        InvokeContext context = new InvokeContext();
        context.setType(msg.getType());
        context.setProtocolCode(ProtocolCode.fromCode(msg.getProtocol()));
        context.setSessionId(msg.getId());
        context.setVersion(msg.getVersion());
        context.setCtx(ctx);
        return context;
    }
}
