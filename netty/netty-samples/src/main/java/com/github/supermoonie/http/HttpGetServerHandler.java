package com.github.supermoonie.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

/**
 * @author supermoonie
 * @date 2020-06-08
 */
public class HttpGetServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) {
        System.out.println("localAddress: " + ctx.channel().localAddress());
        System.out.println("\r\n" + msg);
        if (!msg.decoderResult().isSuccess()) {
            HttpUtils.sendError(ctx, BAD_REQUEST);
            return;
        }
        if (msg.method() != HttpMethod.GET) {
            HttpUtils.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        StringBuilder buf = new StringBuilder();

        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append("HttpGet");
        buf.append("</title></head><body>\r\n");

        buf.append("<h3>");
        buf.append("Hello Http Get!");
        buf.append("</h3>\r\n");

        buf.append("</body></html>\r\n");
        HttpUtils.sendHtml(ctx, buf.toString());
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
