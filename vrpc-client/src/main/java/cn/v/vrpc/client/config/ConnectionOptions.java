package cn.v.vrpc.client.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * v
 * 2019/12/19 上午12:20
 * 1.0
 */
public class ConnectionOptions {
    private Map<String, Object> options = new ConcurrentHashMap<>();

    public void setOptions(String key, String value) {
        options.put(key, value);
    }

    public Object get(String key) {
        return options.get(key);
    }

    public Object getOrDefault(String key, Object defaultValue) {
        return options.get(key) != null ? options.get(key) : defaultValue;
    }

    public boolean containOptions(String key) {
        return options.containsKey(key);
    }



    public interface OptionsKey {
        // common
        String NETTY_HIGH_WATER_MARK = "netty.high.water.mark";
        String NETTY_LOW_WATER_MARK = "netty.low.water.mark";

        String NETTY_BOSS_THREADS= "bossThreads";
        int NETTY_BOSS_THREADS_DEFAULT = 1;

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
        String SERVER_PORT = "port";
        int SERVER_PORT_DEFAULT = 9527;

        String SERVER_ADDRESS = "address";
        String SERVER_ADDRESS_DEFAULT = "127.0.0.1";

    }

}
