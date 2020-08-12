package com.github.supermoonie.mitmproxy.intercept;

import com.github.supermoonie.mitmproxy.intercept.context.InterceptContext;

/**
 * @author supermoonie
 * @since 2020/8/11
 */
public interface MitmProxyIntercept {

    /**
     * on request
     *
     * @param ctx {@link InterceptContext}
     * @return true continue, false break
     */
    boolean onRequest(InterceptContext ctx);

    /**
     * on response
     *
     * @param ctx {@link InterceptContext}
     */
    void onResponse(InterceptContext ctx);

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
     * @return {@link MitmProxyIntercept}
     */
    MitmProxyIntercept next();
}
