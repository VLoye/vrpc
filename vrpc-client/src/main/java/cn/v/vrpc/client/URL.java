package cn.v.vrpc.client;

import cn.v.vrpc.transport.ProtocolCode;

import java.util.HashMap;
import java.util.Map;

/**
 * v
 * 2019/12/13 下午9:28
 * 1.0
 */
public class URL {
    private String host;
    private int port;
//    private ProtocolCode protocol;
//    private int poolSize;
//    private int idleTimeout;

    private String poolKey;

    private Map<String, Object> options = new HashMap<>();

    public URL() {
    }

    public URL(String host, int port) {
        this.host = host;
        this.port = port;
        generatePoolKey();
    }

    private void generatePoolKey() {
        this.poolKey = host + "-" + port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public void putOption(String key, String value) {
        options.put(key, value);
    }

    public <T> T get(String key, T value) {
        return (T) options.get(key);
    }

    public String getPoolKey() {
        return poolKey;
    }

    @Override
    public String toString() {
        return "URL{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", options=" + options +
                '}';
    }
}
