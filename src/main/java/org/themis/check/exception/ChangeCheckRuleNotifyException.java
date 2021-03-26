package org.themis.check.exception;

/**
 * 更改规则通知异常
 * @author yangzhou
 */
public class ChangeCheckRuleNotifyException extends Exception {

    public ChangeCheckRuleNotifyException() {
    }

    public ChangeCheckRuleNotifyException(String message) {
        super(message);
    }

    public ChangeCheckRuleNotifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChangeCheckRuleNotifyException(Throwable cause) {
        super(cause);
    }

    public ChangeCheckRuleNotifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
