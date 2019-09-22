package client;
/**
 * Created by VLoye on 2019/9/3.
 */

import common.RpcUtil;
import core.ServiceStatus;
import core.config.HearBeatConfig;
import core.exc.InitializationException;
import core.exc.RpcException;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import core.message.RpcRequestMessage;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import codec.serialize.DeserializeHandler;
import codec.serialize.JDKSerialize;
import codec.serialize.SerializeHandler;
import server.service.AbstractService;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author V
 * @Classname RpcClient
 * @Description
 **/
public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private String host= "127.0.0.1";
    private int port=9999;
    private ServiceStatus status = ServiceStatus.NEW;
    private ClientConfig config;
    private boolean isOpen;

    public static ConcurrentHashMap<Integer, ResponseFuture> futuresMap = new ConcurrentHashMap<Integer, ResponseFuture>();


    protected Bootstrap bootstrap;
    protected NioEventLoopGroup group;
    protected Channel channel;

    public RpcClient() {
    }

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RpcClient(ClientConfig config) {
        this.config = config;
    }

    private void init() throws InitializationException {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup(2);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new DelimiterBasedFrameDecoder(2048, Unpooled.copiedBuffer(AbstractService.splitDelimiter)));
                        pipeline.addLast(new DeserializeHandler(new JDKSerialize()));
                        pipeline.addLast(new SerializeHandler(new JDKSerialize()));
                        pipeline.addLast(new IdleStateHandler(0,0,30));
                        pipeline.addLast(new CHeartbeatHandler(config.getHearBeatConfig()));
                        pipeline.addLast(new ResponseHandler());
                    }
                });
    }

    private void start() throws InitializationException {
        if(status == ServiceStatus.NEW){
            init();
        }
        ChannelFuture future = null;
        try {
            channel = bootstrap.connect(new InetSocketAddress(host,port)).sync().channel();
            isOpen = true;
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        logger.info("connect to rpc server[{}:{}] success",host,port);
    }

    public boolean isOpen(){
        return isOpen && channel.isActive();
    }

    public ResponseFuture call(RpcRequestMessage message) throws RpcException {
        if(!isOpen()){
            throw new RpcException("client conn is closed.");
        }
        ResponseFuture future = new ResponseFuture();
        futuresMap.put(message.getId(),future);
        try {
            channel.writeAndFlush(message);
        } catch (Throwable e) {
            logger.error(e.getMessage());
        }
//        .addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                logger.debug("send core.message success.");
//            }
//        });
        return future;
    }


    public static void main(String[] args) throws NoSuchMethodException, InterruptedException, ExecutionException, TimeoutException, RpcException {
//        CountDownLatch latch = new CountDownLatch(1);
        ClientConfig clientConfig = new ClientConfig();
        HearBeatConfig hearBeatConfig = new HearBeatConfig();
//        hearBeatConfig.setUseful(true);
        clientConfig.setHearBeatConfig(hearBeatConfig);

        RpcClient client = new RpcClient(clientConfig);
        try {
            client.start();
        } catch (InitializationException e) {
            e.printStackTrace();
        }
        RpcRequestMessage message = new RpcRequestMessage();
        message.setClassName("client.AService");
        message.setParamTypes(new Class[]{String.class});
        message.setArgs(new Object[]{"abcd"});
        message.setMethod(AService.class.getDeclaredMethod("dothing", String.class).getName());
        message.setSessionId(RpcUtil.UUID());
        message.setReply(true);
        ResponseFuture future = client.call(message);
        Object o = future.get(30000, TimeUnit.MILLISECONDS);
        System.out.println(o);
//        latch.await();
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
