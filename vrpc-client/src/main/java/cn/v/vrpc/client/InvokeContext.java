package cn.v.vrpc.client;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InvokeContext implements Serializable {
    private ConcurrentHashMap<String, Object> properties = new ConcurrentHashMap<>();

    public Object get(String key) {
        if (null == key) {
            return null;
        }
        return properties.get(key);
    }

    public Object get(String key, Object o) {
        if (null == key || null == properties.get(key)) {
            return o;
        }
        return properties.get(key);
    }

    public String getAsString(String key) {
        if (null == key) {
            return null;
        }
        return (String) properties.get(key);
    }

    public String getAsString(String key, String s) {
        return getAsString(key) == null ? s : getAsString(key);
    }


    public void put(String key, Object o) {
        properties.put(key, o);
    }

    public void putAll(Map map) {
        properties.putAll(map);
    }

}
