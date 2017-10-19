package exception;

/**
 * This exception is used to indicate that the server is unreachable.
 */
public class ServerUnreachableException extends RuntimeException {
    public ServerUnreachableException() {
    }

    public ServerUnreachableException(String message) {
        super(message);
    }

    public ServerUnreachableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerUnreachableException(Throwable cause) {
        super(cause);
    }

    public ServerUnreachableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
