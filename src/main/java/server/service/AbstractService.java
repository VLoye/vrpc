package server.service;
/**
 * Created by VLoye on 2019/8/17.
 */

import common.RpcUtil;
import core.IService;
import core.ServiceStatus;
import core.config.ServiceConfig;
import handler.HearbeatHandler;
import handler.RemoveConnIdleStateHandler;
import handler.RpcRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import core.message.RpcMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import codec.serialize.DeserializeHandler;
import codec.serialize.JDKSerialize;
import codec.serialize.SerializeHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 服务端service
 *
 * @author V
 * @Classname AbstractService
 * @Description 服务端service
 **/
public abstract class AbstractService implements IService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractService.class);
    protected ServiceConfig config;
    protected ServerBootstrap bootstrap;
    protected Channel serverChannel;
    protected EventLoopGroup boss;
    protected EventLoopGroup worker;
    protected ServiceStatus status = ServiceStatus.NEW;
    public static byte[] splitDelimiter = "_SPLIT_".getBytes();

    public static final AttributeKey<String> ATTR_CHANNEL_KEY = AttributeKey.newInstance("rpc_channel_id");
    public static Map<String,Channel> channelsMap = new ConcurrentHashMap<String,Channel>();

    public AbstractService() {
    }

    public AbstractService(ServiceConfig config) {
        this.config = config;
        init();
    }

    @Override
    public void call(RpcMessage message) {

    }

    @Override
    public void init() {
        bootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        bootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        String uuid = RpcUtil.UUID();
                        channelsMap.put(uuid,socketChannel);
                        socketChannel.attr(ATTR_CHANNEL_KEY).set(uuid);
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(2048,Unpooled.copiedBuffer(splitDelimiter)));
                        pipeline.addLast(new DeserializeHandler(new JDKSerialize()));
                        pipeline.addLast(new SerializeHandler(new JDKSerialize()));
                        pipeline.addLast(new IdleStateHandler(60,0,0));
                        pipeline.addLast(new HearbeatHandler(config.getHearBeatConfig()));
                        pipeline.addLast(new RpcRequestHandler());
                    }
                });
    }

    @Override
    public void start() {
        if(status.equals(ServiceStatus.NEW)){
            init();
        }
        try {
            serverChannel = bootstrap.bind(config.getHost(),config.getPort()).sync().channel();
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
        logger.info("Service start success, bind port[{}]",config.getPort());
    }

    @Override
    public void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

    @Override
    public void setConfig(ServiceConfig config) {
        this.config = config;
    }
}
