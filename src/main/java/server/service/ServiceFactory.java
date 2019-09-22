package server.service;
/**
 * Created by VLoye on 2019/8/17.
 */

import core.IService;
import core.config.ServiceConfig;
import core.exc.InitializationException;
import core.exc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname ServiceFactory
 * @Description
 **/
public class ServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

    //从配置中获取指定类型的service
    public static IService getService(String className, ServiceConfig config) throws RpcException {
        IService service = null;
        try {
            Class<?> clazz = Class.forName(className);
            service = (IService) clazz.newInstance();
            service.setConfig(config);
        } catch (Exception e) {
            throw new InitializationException("Could not found server.service: " + className);
        }
        return service;
    }

}
