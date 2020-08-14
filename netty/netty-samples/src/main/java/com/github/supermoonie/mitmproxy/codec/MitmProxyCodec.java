package com.github.supermoonie.mitmproxy.codec;

import com.github.supermoonie.mitmproxy.ConnectionInfo;
import com.github.supermoonie.mitmproxy.constant.ConnectionState;
import com.github.supermoonie.mitmproxy.intercept.MitmProxyIntercept;
import com.github.supermoonie.mitmproxy.intercept.RealProxyIntercept;
import com.github.supermoonie.mitmproxy.intercept.context.InterceptContext;
import com.github.supermoonie.mitmproxy.util.UriUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;

/**
 * @author supermoonie
 * @date 2020-08-08
 */
public class MitmProxyCodec extends ChannelInboundHandlerAdapter {

    private static final int SSL_FLAG = 22;

    private ConnectionInfo connectionInfo;

    private ConnectionState state = ConnectionState.NOT_CONNECTION;

    private final MitmProxyIntercept intercept;

    public MitmProxyCodec(MitmProxyIntercept intercept) {
        this.intercept = new RealProxyIntercept(intercept);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel clientChannel = ctx.channel();
        InetSocketAddress clientAddress = (InetSocketAddress) clientChannel.remoteAddress();
        String clientHost = clientAddress.getHostString();
        int clientPort = clientAddress.getPort();
        System.out.println("clientHost: " + clientHost + ", clientPort: " + clientPort + " connected");
        connectionInfo = new ConnectionInfo();
        connectionInfo.setClientHost(clientHost);
        connectionInfo.setClientPort(clientPort);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("class: " + msg.getClass().getName() + ", msg: " + msg);
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            ConnectionInfo connectionInfo = UriUtils.parseRemoteInfo(request, this.connectionInfo);
            if (null == connectionInfo) {
                ctx.channel().close();
                return;
            }
            if (state == ConnectionState.NOT_CONNECTION) {
                state = ConnectionState.CONNECTING;
                if (HttpMethod.CONNECT.equals(request.method())) {
                    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                    ctx.writeAndFlush(response);
                    ctx.channel().pipeline().remove(HttpServerCodec.class.getSimpleName());
                    ReferenceCountUtil.release(msg);
                    state = ConnectionState.ALREADY_HANDSHAKE_WITH_CLIENT;
                    return;
                }
            }
            String separator = "/";
            if (request.uri().startsWith(separator)) {
                request.setUri(connectionInfo.isHttps() ? "https://" : "http://" + request.headers().get(HttpHeaderNames.HOST) + request.uri());
            }
            System.out.println("uri: " + request.uri());
            InterceptContext context = new InterceptContext(ctx.channel(), msg, connectionInfo);
            doInterceptRequest(context);
            state = ConnectionState.CONNECTED;
        } else if (msg instanceof HttpContent) {
            if (state != ConnectionState.CONNECTED) {
                InterceptContext context = new InterceptContext(ctx.channel(), msg, connectionInfo);
                doInterceptRequest(context);
            } else {
                ReferenceCountUtil.release(msg);
                state = ConnectionState.ALREADY_HANDSHAKE_WITH_CLIENT;
            }
        } else {
            ByteBuf byteBuf = (ByteBuf) msg;
            if (SSL_FLAG == byteBuf.getByte(0)) {
                connectionInfo.setHttps(true);
            }
            InterceptContext context = new InterceptContext(ctx.channel(), msg, connectionInfo);
            doInterceptRequest(context);
        }
    }

    private void doInterceptRequest(InterceptContext context) {
        MitmProxyIntercept currentIntercept = this.intercept;
        while (null != currentIntercept) {
            currentIntercept.onRequest(context);
            currentIntercept = currentIntercept.next();
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
