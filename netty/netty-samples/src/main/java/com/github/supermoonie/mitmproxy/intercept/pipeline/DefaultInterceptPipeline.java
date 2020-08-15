package com.github.supermoonie.mitmproxy.intercept.pipeline;

import com.github.supermoonie.mitmproxy.intercept.InternalProxyIntercept;

import java.util.Iterator;
import java.util.Map;

/**
 * @author supermoonie
 * @since 2020/8/15
 */
public class DefaultInterceptPipeline implements InterceptPipeline {



    @Override
    public Iterator<Map.Entry<String, InternalProxyIntercept>> iterator() {
        return null;
    }
}
