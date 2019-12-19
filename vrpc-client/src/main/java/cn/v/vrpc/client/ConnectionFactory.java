package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.config.OptionsKey;
import cn.v.vrpc.transport.BaseDecoder;
import cn.v.vrpc.transport.BaseEncoder;
import cn.v.vrpc.transport.ProtocolCode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.rmi.RemoteException;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public class ConnectionFactory {
    private Bootstrap bootstrap;
    private ConnectionOptions options;
    private NioEventLoopGroup worker;

    public ConnectionFactory(ConnectionOptions options) {
        this.options = options;
        bootstrap = new Bootstrap();
        worker = new NioEventLoopGroup((Integer) options.getOrDefault(OptionsKey.NETTY_IO_THREADS, 0));
        init();
    }

    private void init() {
        initConfigOptions();
        String protocolName = (String) options.getOrDefault(OptionsKey.CONNECTION_PROTOCOL, "rpc");
//        int idleTimeout = Integer.valueOf(options.getOrDefault(OptionsKey.CONNECTION_IDLE_TIMEOUT, OptionsKey.CONNECTION_IDLE_TIMEOUT_DEFAULT));
        bootstrap.channel(NioSocketChannel.class)
                .group(worker)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast("BaseEncoder", new BaseEncoder(ProtocolCode.formName(protocolName)));
                        pipeline.addLast("BaseDecoder", new BaseDecoder());
//                        pipeline.addLast("IdleHandler", new IdleStateHandler());
                    }
                });

    }

    public Connection createConnection(URL url) throws Exception {
        if (url == null) {
            throw new Exception("url can't be null");
        }
        Channel channel = doCreate(url);

        return new NettyConnection(channel);
    }

    private void initConfigOptions() {
        if (options.containOptions(OptionsKey.NETTY_HIGH_WATER_MARK) || options.containOptions(OptionsKey.NETTY_LOW_WATER_MARK)) {
            int highMark = (Integer) options.getOrDefault(OptionsKey.NETTY_HIGH_WATER_MARK, 32768);
            int lowMark = (Integer) options.getOrDefault(OptionsKey.NETTY_LOW_WATER_MARK, 65536);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(lowMark, highMark));
        }

    }

    private Channel doCreate(URL url) throws Exception {
        String targetHost = url.getHost();
        Integer targetPort = url.getPort();
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(targetHost, targetPort)).sync();
        if (!future.isSuccess()) {
            throw new Exception(future.cause().getMessage());
        }
        return future.channel();
    }
}
