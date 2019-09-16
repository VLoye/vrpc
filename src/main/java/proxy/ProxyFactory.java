package proxy;

import core.InvocationHandlerFactory;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by VLoye on 2019/8/17.
 */
public class ProxyFactory {
    public static Map<String,Object> proxyMap = new ConcurrentHashMap<String,Object>();

    public static Object getClientProxy(Class clazz) {
        return Proxy.newProxyInstance(ProxyFactory.class.getClassLoader(), new Class<?>[]{clazz}, InvocationHandlerFactory.getInvocation());
    }

//    public static <T> T getServerProxy(Class<?> className){
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        Class<?>[] interfaces = new Class<?>[]{className};
//
//        return
//    }

    public static Object getInstance(String clazz){
        return proxyMap.get(clazz);
    }

    public static void registerInstance(Class<?> clazz){
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException e) {
            //// TODO: 2019/8/26 设计时未考虑异常
        } catch (IllegalAccessException e) {
        }
        proxyMap.put(clazz.getName(),instance);
    }
}
