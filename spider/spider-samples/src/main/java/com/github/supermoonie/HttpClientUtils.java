package com.github.supermoonie;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

/**
 * @author supermoonie
 * @since 2020/7/14
 */
public class HttpClientUtils {

    public static HttpClientBuilder createTrustAllHttpClientBuilder() {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, (chain, authType) -> true);
            SSLConnectionSocketFactory factory = new
                    SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
            return HttpClients.custom().setSSLSocketFactory(factory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
