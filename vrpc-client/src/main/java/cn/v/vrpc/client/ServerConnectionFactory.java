package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.config.OptionsKey;
import cn.v.vrpc.transport.BaseDecoder;
import cn.v.vrpc.transport.BaseEncoder;
import cn.v.vrpc.transport.ProtocolCode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * v
 * 2019/12/26 下午11:45
 * 1.0
 */
public class ServerConnectionFactory extends ConnectionFactory {
    private ServerBootstrap serverBootstrap;
    private NioEventLoopGroup boss;
    private ChannelFuture channelFuture;


    public ServerConnectionFactory(ConnectionOptions options,HeartbeatTrigger heartbeatTrigger) {
        super(options,heartbeatTrigger);
    }

    public void start() {
        serverBootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup((int) options.getOrDefault(ConnectionOptions.OptionsKey.NETTY_BOSS_THREADS, ConnectionOptions.OptionsKey.NETTY_BOSS_THREADS_DEFAULT));
        String protocolName = (String) options.getOrDefault(OptionsKey.CONNECTION_PROTOCOL, OptionsKey.CONNECTION_PROTOCOL_DEFAULT);
        initializeConfigs();
        serverBootstrap.channel(NioServerSocketChannel.class)
                .group(boss, worker)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();

                        pipeline.addLast("BaseEncoder", new BaseEncoder(ProtocolCode.formName(protocolName)));
                        pipeline.addLast("BaseDecoder", new BaseDecoder());

                    }
                });
        String address = (String) options.getOrDefault(ConnectionOptions.OptionsKey.SERVER_ADDRESS, ConnectionOptions.OptionsKey.SERVER_ADDRESS_DEFAULT);
        int port = (int) options.getOrDefault(ConnectionOptions.OptionsKey.SERVER_PORT, ConnectionOptions.OptionsKey.SERVER_PORT_DEFAULT);
        channelFuture = serverBootstrap.bind(new InetSocketAddress(address, port)).awaitUninterruptibly();

    }

    public void initializeConfigs() {
        if (options.containOptions(OptionsKey.NETTY_HIGH_WATER_MARK) || options.containOptions(OptionsKey.NETTY_LOW_WATER_MARK)) {
            int highMark = (Integer) options.getOrDefault(OptionsKey.NETTY_HIGH_WATER_MARK, 32768);
            int lowMark = (Integer) options.getOrDefault(OptionsKey.NETTY_LOW_WATER_MARK, 65536);
            serverBootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(lowMark, highMark));
        }
    }

}
