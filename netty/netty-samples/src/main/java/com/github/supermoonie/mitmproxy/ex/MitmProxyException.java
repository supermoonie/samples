package com.github.supermoonie.mitmproxy.ex;

/**
 * @author supermoonie
 * @date 2020-08-08
 */
public class MitmProxyException extends RuntimeException {

    public MitmProxyException() {
        super();
    }

    public MitmProxyException(String message) {
        super(message);
    }

    public MitmProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MitmProxyException(Throwable cause) {
        super(cause);
    }

    protected MitmProxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
