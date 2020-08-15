package com.github.supermoonie.mitmproxy.intercept.pipeline;

import com.github.supermoonie.mitmproxy.intercept.InternalProxyIntercept;

import java.util.Map;

/**
 * @author supermoonie
 * @since 2020/8/15
 */
public interface InterceptPipeline extends Iterable<Map.Entry<String, InternalProxyIntercept>> {
}
