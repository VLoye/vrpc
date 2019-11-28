package core.exc;/**
 * Created by V on 2019/11/28.
 */

/**
 * V
 * 2019/11/28 1:16
 */
public class RequestTimeoutException extends ConnectionException {
    public RequestTimeoutException() {
    }

    public RequestTimeoutException(String message) {
        super(message);
    }

    public RequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestTimeoutException(Throwable cause) {
        super(cause);
    }

    public RequestTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
