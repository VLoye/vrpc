package cn.v.vrpc.client;

import cn.v.vrpc.client.config.ConnectionOptions;
import cn.v.vrpc.client.config.OptionsKey;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;

/**
 * v
 * 2019/12/16 下午10:12
 * 1.0
 */
public class ConnectionFactory {
    private Bootstrap bootstrap;
    private ConnectionOptions options;

    public ConnectionFactory(ConnectionOptions options) {
        this.options = options;
        bootstrap = new Bootstrap();
        initConfigOptions();
    }

    public Connection createConnection(URL url, ConnectionOptions options) {
        return null;
    }

    private void initConfigOptions() {
        if (options.containOptions(OptionsKey.NETTY_HIGH_WATER_MARK) || options.containOptions(OptionsKey.NETTY_LOW_WATER_MARK)) {
            int highMark = (Integer) options.getOrDefault(OptionsKey.NETTY_HIGH_WATER_MARK, 32768);
            int lowMark = (Integer) options.getOrDefault(OptionsKey.NETTY_LOW_WATER_MARK, 65536);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(lowMark, highMark));
        }

    }
}
