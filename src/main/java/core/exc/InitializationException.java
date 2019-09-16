package core.exc;
/**
 * Created by VLoye on 2019/9/2.
 */

/**
 * @author V
 * @Classname InitializationException
 * @Description
 **/
public class InitializationException extends RpcException {
    public InitializationException() {

    }

    public InitializationException(String message) {
        super(message);
    }

    public InitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
