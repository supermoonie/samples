package com.github.supermoonie.mitmproxy.intercept;

import com.github.supermoonie.mitmproxy.intercept.context.InterceptContext;

/**
 * @author supermoonie
 * @since 2020/8/12
 */
public abstract class AbstractIntercept implements MitmProxyIntercept {

    @Override
    public boolean onRequest(InterceptContext ctx) {
        MitmProxyIntercept intercept = next();
        if (null != intercept) {
            return intercept.onRequest(ctx);
        }
        return true;
    }

    @Override
    public void onResponse(InterceptContext ctx) {
        MitmProxyIntercept intercept = next();
        if (null != intercept) {
            intercept.onResponse(ctx);
        }
    }

    @Override
    public void onException(InterceptContext ctx, Throwable cause) throws Throwable {
        MitmProxyIntercept intercept = next();
        if (null != intercept) {
            intercept.onException(ctx, cause);
        }
    }
}
