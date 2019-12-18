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

}
