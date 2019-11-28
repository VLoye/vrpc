package core;/**
 * Created by V on 2019/11/27.
 */

/**
 * V
 * 2019/11/27 17:59
 */
public class LifeCycleException extends Throwable{
    public LifeCycleException() {
    }

    public LifeCycleException(String message) {
        super(message);
    }

    public LifeCycleException(String message, Throwable cause) {
        super(message, cause);
    }

    public LifeCycleException(Throwable cause) {
        super(cause);
    }

    public LifeCycleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
