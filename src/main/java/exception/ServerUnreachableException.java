package exception;

/**
 * Created by softish on 2017-10-07.
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
