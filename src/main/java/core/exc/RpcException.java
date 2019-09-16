package core.exc;
/**
 * Created by VLoye on 2019/9/2.
 */

/**
 * @author V
 * @Classname RpcException
 * @Description
 **/
public class RpcException extends Throwable {
    public RpcException() {
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
