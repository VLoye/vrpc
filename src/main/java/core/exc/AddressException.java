package core.exc;/**
 * Created by V on 2019/11/28.
 */

/**
 * V
 * 2019/11/28 1:06
 */
public class AddressException extends ConnectionException {
    public AddressException() {
    }

    public AddressException(String message) {
        super(message);
    }

    public AddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressException(Throwable cause) {
        super(cause);
    }

    public AddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
