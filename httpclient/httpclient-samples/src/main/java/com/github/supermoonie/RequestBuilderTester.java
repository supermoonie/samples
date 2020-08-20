package com.github.supermoonie;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author supermoonie
 * @since 2020/8/9
 */
public class RequestBuilderTester {

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

    public static void main(String[] args) throws URISyntaxException, IOException {
        RequestBuilder builder = RequestBuilder.create("GET").setUri(new URI("https://223.100.185.98:443/wt-web-gr/captcha"));
        CloseableHttpClient httpClient = createTrustAllHttpClientBuilder().build();
        CloseableHttpResponse response = httpClient.execute(builder.build());
        System.out.println(response.getStatusLine());
    }
}
