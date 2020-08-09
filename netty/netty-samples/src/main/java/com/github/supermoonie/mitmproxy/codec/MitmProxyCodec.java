package com.github.supermoonie.mitmproxy.codec;

import com.github.supermoonie.mitmproxy.RequestInfo;
import com.github.supermoonie.mitmproxy.constant.RemoteConnectionState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author supermoonie
 * @date 2020-08-08
 */
public class MitmProxyCodec extends ChannelInboundHandlerAdapter {

    private RequestInfo requestInfo = new RequestInfo();

    private RemoteConnectionState state = RemoteConnectionState.NOT_CONNECTION;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel clientChannel = ctx.channel();
        InetSocketAddress clientAddress = (InetSocketAddress) clientChannel.remoteAddress();
        String clientHost = clientAddress.getHostString();
        int clientPort = clientAddress.getPort();
        requestInfo.setClientHost(clientHost);
        requestInfo.setClientPort(clientPort);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            HttpMethod method = request.method();
            requestInfo.setMethod(method);
            String uri = request.uri();
            requestInfo.setUri(uri);
            if (state == RemoteConnectionState.NOT_CONNECTION) {

            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
