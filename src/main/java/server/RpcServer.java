package server; /**
 * Created by VLoye on 2019/8/6.
 */

import core.IService;
import core.config.RpcConfig;
import core.config.ServiceConfig;
import core.exc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.service.ServiceFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author V
 * @Classname server.server.RpcServer
 * @Description
 **/
public class RpcServer {
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private static final int MAX_QUEUE_SIZE = 1024;

    protected RpcConfig config;
    protected static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(MAX_QUEUE_SIZE);
    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 8, 30000, TimeUnit.MILLISECONDS, queue);
    public static Map<String, Object> interfaceCacheMap = new ConcurrentHashMap<String, Object>();

    public RpcServer(RpcConfig config) throws RpcException{
        this.config = config;
    }

    public void start() throws RpcException{
        List<ServiceConfig> serviceConfigs = config.getConfigs();
        for (ServiceConfig config : serviceConfigs) {
            IService service = null;
            try {
                service = ServiceFactory.getService(config.getServiceClassName(), config);
            } catch (RpcException e) {
                logger.warn("Service[{}] start fail, cause by[{}]", config.getServiceClassName(), e);
            }
            service.start();
        }
    }

    public void registerInterface(Class<?> clazz, Object instace) {
        interfaceCacheMap.put(clazz.getName(), instace);
    }

    public void registerInterface(Class<?> clazz) {
        Object o = null;
        try {
            o = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        this.registerInterface(clazz, o);
    }

}
