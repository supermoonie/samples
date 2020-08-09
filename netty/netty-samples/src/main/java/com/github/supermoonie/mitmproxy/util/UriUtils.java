package com.github.supermoonie.mitmproxy.util;

import com.github.supermoonie.mitmproxy.RequestInfo;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * @author supermoonie
 * @since 2020/8/9
 */
public final class UriUtils {

    /**
     * httpbin.org:443
     * /get
     * https://httpbin.org/get
     * http://httpbin.org/get
     */
    private static final Pattern HOST_PORT_PATTERN = Pattern.compile("");

    private UriUtils() {
    }

    public static RequestInfo parseHttpRequest(HttpRequest request, RequestInfo info) {
        if (null == info) {
            info = new RequestInfo();
        }
        HttpMethod method = request.method();
        info.setMethod(method);
        String host = request.headers().get(HttpHeaderNames.HOST);
        if (null != host) {
            info.setClientHost(host);
        }
        String uri = request.uri();
        if (HttpMethod.CONNECT == method) {

        } else {
            info.setUri(uri);
        }
        return info;
    }

    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("https://223.100.185.98:443/wt-web-gr/captcha");
        System.out.println(uri.toString());
    }
}
