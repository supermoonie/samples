package com.github.supermoonie.mitmproxy.intercept;

/**
 * @author supermoonie
 * @since 2020/8/15
 */
public interface InterceptPipeline extends Intercept {

    InterceptPipeline addFirst(AbstractIntercept intercept);

    InterceptPipeline addLast(AbstractIntercept intercept);
}
