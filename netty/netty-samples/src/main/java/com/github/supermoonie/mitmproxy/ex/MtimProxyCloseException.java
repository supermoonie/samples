package com.github.supermoonie.mitmproxy.ex;

/**
 * @author supermoonie
 * @date 2020-08-08
 */
public class MtimProxyCloseException extends MitmProxyException {

    public MtimProxyCloseException() {
        super();
    }

    public MtimProxyCloseException(String message) {
        super(message);
    }

    public MtimProxyCloseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MtimProxyCloseException(Throwable cause) {
        super(cause);
    }

    protected MtimProxyCloseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
