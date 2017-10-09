package exception;

/**
 * Created by softish on 2017-10-09.
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
