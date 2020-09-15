package cn.v.vrpc.client.rpc;

import cn.v.vrpc.client.InvokeContext;
import cn.v.vrpc.client.RemotingProcessor;
import cn.v.vrpc.protocol.ProtocolCode;
import cn.v.vrpc.protocol.Transportable;
import cn.v.vrpc.protocol.rpc.RpcMessageFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * v
 * 2020/1/13 下午11:30
 * 1.0
 */
public class ResponseHandler extends SimpleChannelInboundHandler<Transportable> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private RemotingProcessor userProcessor;

    public ResponseHandler(RemotingProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Transportable msg) throws Exception {
        if (msg instanceof RpcMessageFrame) {
            RpcMessageFrame frame = (RpcMessageFrame) msg;
            InvokeContext invokeContext = initContext(frame, ctx);
            userProcessor.process(invokeContext, frame);
        } else {
            logger.warn("unknown msg type: {}", msg.getClass().toString());
        }
    }

    private InvokeContext initContext(RpcMessageFrame frame, ChannelHandlerContext ctx) {
        InvokeContext context = new InvokeContext();
        context.setCtx(ctx);
        context.setSessionId(frame.getId());
        context.setProtocolCode(ProtocolCode.fromCode(frame.getProtocol()));
        context.setVersion(frame.getVersion());
        context.setType(frame.getType());
        return context;
    }

}
