package handler;
/**
 * Created by VLoye on 2019/8/21.
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author V
 * @Classname ServerInvocationHandler
 * @Description
 **/
public class ServerInvocationHandler<T> implements InvocationHandler{

    private T instance;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
