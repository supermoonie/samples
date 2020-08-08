package com.github.supermoonie.mitmproxy.ex;

/**
 * @author supermoonie
 * @date 2020-08-08
 */
public class MitmProxyStartException extends MitmProxyException {

    public MitmProxyStartException() {
        super();
    }

    public MitmProxyStartException(String message) {
        super(message);
    }

    public MitmProxyStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public MitmProxyStartException(Throwable cause) {
        super(cause);
    }

    protected MitmProxyStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
