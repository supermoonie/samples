package com.github.supermoonie.mitmproxy.intercept;

import com.github.supermoonie.mitmproxy.intercept.context.InterceptContext;

/**
 * @author supermoonie
 * @since 2020/8/11
 */
public interface InternalProxyIntercept {

    /**
     * on active
     *
     * @param ctx {@link InterceptContext}
     */
    void onActive(InterceptContext ctx);

    /**
     * on request
     *
     * @param ctx {@link InterceptContext}
     * @param msg request msg
     * @return true continue, false break
     */
    boolean onRequest(InterceptContext ctx, Object msg);

    /**
     * on response
     *
     * @param ctx {@link InterceptContext}
     * @param msg response msg
     */
    void onResponse(InterceptContext ctx, Object msg);

    /**
     * on exception
     *
     * @param ctx   {@link InterceptContext}
     * @param cause {@link Throwable}
     * @throws Throwable t
     */
    void onException(InterceptContext ctx, Throwable cause) throws Throwable;

    /**
     * next
     *
     * @return {@link InternalProxyIntercept}
     */
    InternalProxyIntercept next();
}
