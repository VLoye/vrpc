package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.config.OptionsKey;
import cn.v.vrpc.client.rpc.ResponseHandler;
import cn.v.vrpc.client.rpc.UserProcessor;
import cn.v.vrpc.protocol.ProtocolCode;
import cn.v.vrpc.transport.BaseDecoder;
import cn.v.vrpc.transport.BaseEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public class ConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);
    protected Bootstrap bootstrap;
    @Deprecated
    protected ConnectionOptions options;
    //    protected Option option;
    protected NioEventLoopGroup worker;

    protected HeartbeatTrigger heartbeatTrigger;
    protected ConnectionEventListener connectionEventListener;



    public static final AttributeKey<String> KEY_REQUEST_TIMEOUT = AttributeKey.newInstance("requestTimeout");
    public static final AttributeKey<ConnectionOptions> KEY_CONNECTION_OPTIONS = AttributeKey.newInstance("connectionOptions");

    public ConnectionFactory(ConnectionOptions options, HeartbeatTrigger heartbeatTrigger, ConnectionEventListener connectionEventListener) {
        this.options = options;
        this.heartbeatTrigger = heartbeatTrigger;
        this.connectionEventListener = connectionEventListener;
        worker = new NioEventLoopGroup((Integer) options.getOrDefault(OptionsKey.NETTY_IO_THREADS, 0));
        init();
    }


    public ConnectionFactory(ConnectionOptions options, HeartbeatTrigger heartbeatTrigger) {
        this(options,heartbeatTrigger,null);
    }

    private void init() {
        bootstrap = new Bootstrap();
        initConfigOptions();
        String protocolName = (String) options.getOrDefault(OptionsKey.CONNECTION_PROTOCOL, OptionsKey.CONNECTION_PROTOCOL_DEFAULT);
        bootstrap.channel(NioSocketChannel.class)
                .group(worker)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("BaseEncoder", new BaseEncoder(ProtocolCode.formName(protocolName)));
                        pipeline.addLast("BaseDecoder", new BaseDecoder());
                        if (options.containOptions(ConnectionOptions.OptionsKey.CONNECTION_IDLE_TIMEOUT)) {
                            int idleTimeout = (int) options.getOrDefault(OptionsKey.CONNECTION_IDLE_TIMEOUT, OptionsKey.CONNECTION_IDLE_TIMEOUT_DEFAULT);
                            pipeline.addLast("IdleHandler", new IdleStateHandler(idleTimeout, idleTimeout, 0));
                            pipeline.addLast("HeartbeatHandler", new HeartbeatHandler(heartbeatTrigger));
                        }
                        pipeline.addLast("responseHandler", new ResponseHandler(new UserProcessor()));
                        if (connectionEventListener != null) {
                            pipeline.addLast("ChannelEventHandler", new ChannelEventHandler(connectionEventListener));
                        }
                    }
                });

    }

    public Connection createConnection(URL url) throws Exception {
        if (url == null) {
            throw new Exception("url can't be null");
        }
        Channel channel = connect(url);

        return new NettyConnection(url, channel);
    }

    public boolean reconnect(Connection connection) {
        URL url = connection.URL();
        try {
            connection.setChannel(connect(url));
        } catch (Exception e) {
            logger.warn("reconnect to server[{}] failed, cause by: {}", url, e.getMessage());
            return false;
        }
        logger.info("reconnect to server[{}] succeed", url);
        return true;
    }

    private void initConfigOptions() {
        if (options.containOptions(OptionsKey.NETTY_HIGH_WATER_MARK) || options.containOptions(OptionsKey.NETTY_LOW_WATER_MARK)) {
            int highMark = (int) options.getOrDefault(OptionsKey.NETTY_HIGH_WATER_MARK, 32768);
            int lowMark = (int) options.getOrDefault(OptionsKey.NETTY_LOW_WATER_MARK, 65536);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(lowMark, highMark));
        }

    }

    private Channel connect(URL url) throws Exception {
        String targetHost = url.getHost();
        Integer targetPort = url.getPort();
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(targetHost, targetPort)).sync();
        if (!future.isSuccess()) {
            throw new Exception(future.cause().getMessage());
        }
        logger.info("connect to [{}:{}]", url.getHost(), url.getPort());
        return future.channel();
    }
}
