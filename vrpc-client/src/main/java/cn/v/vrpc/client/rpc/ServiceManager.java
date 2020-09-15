package cn.v.vrpc.client.rpc;

import java.util.HashMap;

/**
 * v
 * 2020/1/13 上午12:54
 * 1.0
 */
public class ServiceManager {
    private HashMap<String, Object> servicesMap = new HashMap<>();

    public void registerService(Class<?> interfaceClass, Object target) {
        servicesMap.put(interfaceClass.getName(), target);
    }

    public Object getTarget(String interfaceClass) {
        return servicesMap.get(interfaceClass);
    }
}
