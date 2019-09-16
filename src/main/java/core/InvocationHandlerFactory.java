package core;
/**
 * Created by VLoye on 2019/8/17.
 */

import handler.ClientInvocationHandler;

import java.lang.reflect.InvocationHandler;

/**
 * @author V
 * @Classname InvocationHandlerFactory
 * @Description
 **/
public class InvocationHandlerFactory {

    public static InvocationHandler getInvocation() {
        return new ClientInvocationHandler();
    }
}
