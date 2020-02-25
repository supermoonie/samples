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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author supermoonie
 * @since 2020-02-18
 */
public class HcApp {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String result = Request.Get("https://httpbin.org/get")
                .setHeader(HttpHeaders.USER_AGENT, "bar")
                .execute()
                .handleResponse(new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                return EntityUtils.toString(response.getEntity());
            }
        });
        System.out.println(result);
        Future<Content> execute = Async.newInstance().execute(Request.Get(""));
        Content content = Async.newInstance().execute(Request.Get("https://httpbin.org/get")).get();
        System.out.println(content.asString());
    }
}
