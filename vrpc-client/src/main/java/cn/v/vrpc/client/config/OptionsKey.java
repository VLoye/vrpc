package cn.v.vrpc.client.config;

@Deprecated
public interface OptionsKey {
    // common
    String NETTY_HIGH_WATER_MARK = "netty.high.water.mark";
    String NETTY_LOW_WATER_MARK = "netty.low.water.mark";

    String NETTY_IO_THREADS = "ioThreads";
    int NETTY_IO_THREADS_DEFAULT = 0;

    String CONNECTION_POOL_SIZE = "size";
    int CONNECTION_POOL_SIZE_DEFAULT = 1;

    String CONNECTION_PROTOCOL = "protocol";
    String CONNECTION_PROTOCOL_DEFAULT = "rpc";


    String CONNECTION_IDLE_TIMEOUT = "idleTimeout";
    int CONNECTION_IDLE_TIMEOUT_DEFAULT = 60 * 1000;

    // client


    // server

}
