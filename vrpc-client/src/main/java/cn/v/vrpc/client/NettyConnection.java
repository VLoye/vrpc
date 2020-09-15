package cn.v.vrpc.client;


import cn.v.vrpc.protocol.Transportable;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * v
 * 2019/12/20 上午12:38
 * 1.0
 */
public class NettyConnection implements Connection {
    private final static Logger logger = LoggerFactory.getLogger(NettyConnection.class);
    private URL url;
    private volatile Channel channel;

    private static final Long MAX_SESSION_TIMEOUT = 2 * 60 * 1000L;

//    private AtomicInteger integer = new AtomicInteger(0);

    private long executeTimes;
    private long maxExecuteTime;
    private long minExecuteTime;
    // 平均
    private long averageTimes;


    private static final ChannelFutureListener INVOKE_LISTENER = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            logger.debug("message[cid={}] send to server success.", future.channel().id().asShortText());
        }
    };

    public NettyConnection(URL url, Channel channel) {
        this.url = url;
        this.channel = channel;
    }


    public Object syncInvoke(Transportable msg) throws RemotingException, InterruptedException {
        ResponseFuture future = asyncInvoke(msg);
        return future.get();
    }

    @Override
    public Object syncInvoke(Transportable msg, long timeout) throws RemotingException, TimeoutException, InterruptedException {
        timeout = timeout > 0 ? timeout : MAX_SESSION_TIMEOUT;
        ResponseFuture future = asyncInvoke(msg, timeout);
        return future.get(timeout);
    }

    @Override
    public ResponseFuture asyncInvoke(Transportable msg) throws RemotingException {
        return asyncInvoke(msg, MAX_SESSION_TIMEOUT);
    }

    public ResponseFuture asyncInvoke(Transportable msg, long timeout) throws RemotingException {
        logger.debug(msg.toString());
        ResponseFuture responseFuture = new ResponseFuture(msg.id(), timeout > 0 ? timeout : MAX_SESSION_TIMEOUT);
        channel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    responseFuture.fail(future.cause());
                }
            }
        });
        return responseFuture;
    }

    @Override
    public boolean isActive() {
        return channel != null && channel.isActive();
    }

    @Override
    public URL URL() {
        return this.url;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
