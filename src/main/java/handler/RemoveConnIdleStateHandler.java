package handler;
/**
 * Created by VLoye on 2019/9/16.
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.service.AbstractService;

import java.util.concurrent.TimeUnit;

/**
 * @author V
 * @Classname RemoveConnIdleStateHandler
 * @Description
 **/
public class RemoveConnIdleStateHandler extends IdleStateHandler{
    private static final Logger logger = LoggerFactory.getLogger(RemoveConnIdleStateHandler.class);

    public RemoveConnIdleStateHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    public RemoveConnIdleStateHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    public RemoveConnIdleStateHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(observeOutput, readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            logger.warn("The conn idle timeout and will be closed.");
            AbstractService.channelsMap.remove(ctx.channel().attr(AbstractService.ATTR_CHANNEL_KEY).get());
            ctx.channel().close();
        }
    }
}
