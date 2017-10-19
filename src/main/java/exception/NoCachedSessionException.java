package exception;

/**
 * This exception is used to indicate that no cached session
 * is present.
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
