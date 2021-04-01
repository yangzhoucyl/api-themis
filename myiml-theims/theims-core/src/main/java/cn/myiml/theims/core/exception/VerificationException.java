package cn.myiml.theims.core.exception;

/**
 * 校验异常
 * @author yangzhou
 */
public class VerificationException extends Exception {

    public VerificationException() {
    }

    public VerificationException(String message) {
        super(message);
    }

    public VerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationException(Throwable cause) {
        super(cause);
    }

    public VerificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
