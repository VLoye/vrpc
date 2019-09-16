package core;
/**
 * Created by VLoye on 2019/8/30.
 */

import message.RpcRequestMessage;
import message.RpcResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.RpcServer;
import service.AbstractService;

import java.lang.reflect.Method;

/**
 * @author V
 * @Classname InvocationTask
 * @Description
 **/
public class InvocationTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(InvocationTask.class);
    private RpcRequestMessage msg;

    public InvocationTask(RpcRequestMessage message) {
        this.msg = message;
    }

    @Override
    public void run() {
        Object instance = RpcServer.interfaceCacheMap.get(msg.getClassName());
//        System.out.println(instance == null);
        Method method = null;
        try {
            method = instance.getClass().getMethod(msg.getMethod(), msg.getParamTypes());
        } catch (NoSuchMethodException e) {
            logger.error("Could not found target method[{}-{}],cause by: {}", msg.getMethod(), msg.getParamTypes(),e);
            return;
        }
        method.setAccessible(true);
        boolean isSuccess = true;
        Object result = null;
        try {
            result = method.invoke(instance, msg.getArgs());
            logger.info(result.toString());
        } catch (Exception e) {
            logger.error("Invocation failure, cause by: {}", e);
            isSuccess = false;
            result = e.getMessage();
        }

        RpcResponseMessage response = new RpcResponseMessage();
        response.fillAttributes(msg);
        response.setSuccess(isSuccess);
        response.setResult(result);
        AbstractService.channelsMap.get(msg.getSessionId()).writeAndFlush(response);
    }

//    private Class<?>[] getParamsTypes(Object[] args) {
//        Class<?>[] classes = new Class[args.length];
//        for (int i = 0; i < args.length; i++) {
//            classes[i] = args.getClass();
//        }
//        return classes;
//    }
}
