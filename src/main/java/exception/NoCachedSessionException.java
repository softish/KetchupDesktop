package exception;

/**
 * Created by softish on 2017-10-14.
 */
public class NoCachedSessionException extends RuntimeException {
    public NoCachedSessionException() {
    }

    public NoCachedSessionException(String message) {
        super(message);
    }

    public NoCachedSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCachedSessionException(Throwable cause) {
        super(cause);
    }

    public NoCachedSessionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
