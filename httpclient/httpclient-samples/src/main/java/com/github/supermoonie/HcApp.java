package com.github.supermoonie;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author supermoonie
 * @since 2020-02-18
 */
public class HcApp {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String result = Request.Get("http://127.0.0.1:8080")
//                .viaProxy("http://127.0.0.1:10801")
                .setHeader(HttpHeaders.USER_AGENT, "bar")
                .execute()
                .handleResponse(response -> {
                    System.out.println(response.getStatusLine());
                    System.out.println(Arrays.toString(response.getAllHeaders()));
                    return EntityUtils.toString(response.getEntity());
                });
        System.out.println(result);
    }
}
