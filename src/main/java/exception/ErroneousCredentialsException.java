package exception;

/**
 * This exception indicates that the provided credentials are wrong.
 */
public class ErroneousCredentialsException extends RuntimeException {
    public ErroneousCredentialsException() {
    }

    public ErroneousCredentialsException(String message) {
        super(message);
    }

    public ErroneousCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErroneousCredentialsException(Throwable cause) {
        super(cause);
    }

    public ErroneousCredentialsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
