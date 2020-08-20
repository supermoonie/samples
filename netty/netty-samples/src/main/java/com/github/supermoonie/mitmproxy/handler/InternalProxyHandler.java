package com.github.supermoonie.mitmproxy.handler;

import com.github.supermoonie.mitmproxy.ConnectionInfo;
import com.github.supermoonie.mitmproxy.constant.ConnectionState;
import com.github.supermoonie.mitmproxy.intercept.*;
import com.github.supermoonie.mitmproxy.util.RequestUtils;
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
public class InternalProxyHandler extends ChannelInboundHandlerAdapter {

    private static final int SSL_FLAG = 22;

    private final InterceptPipeline interceptPipeline = new DefaultInterceptPipeline();

    private final InterceptContext interceptContext = new InterceptContext(interceptPipeline);

    private ConnectionState state = ConnectionState.NOT_CONNECTION;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel clientChannel = ctx.channel();
        InetSocketAddress clientAddress = (InetSocketAddress) clientChannel.remoteAddress();
        String clientHost = clientAddress.getHostString();
        int clientPort = clientAddress.getPort();
        ConnectionInfo connectionInfo = new ConnectionInfo();
        connectionInfo.setClientHost(clientHost);
        connectionInfo.setClientPort(clientPort);
        interceptContext.setConnectionInfo(connectionInfo);
        interceptContext.setClientChannel(clientChannel);
        interceptPipeline.addFirst(new RealProxyIntercept());
        interceptPipeline.onActive(interceptContext);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("class: " + msg.getClass().getName() + ", msg: " + msg);
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            ConnectionInfo connectionInfo = RequestUtils.parseRemoteInfo(request, interceptContext.getConnectionInfo());
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
            interceptPipeline.onRequest(interceptContext, msg);
            state = ConnectionState.CONNECTED;
        } else if (msg instanceof HttpContent) {
            if (state != ConnectionState.CONNECTED) {
                interceptPipeline.onRequest(interceptContext, msg);
            } else {
                ReferenceCountUtil.release(msg);
                state = ConnectionState.ALREADY_HANDSHAKE_WITH_CLIENT;
            }
        } else {
            ByteBuf byteBuf = (ByteBuf) msg;
            if (SSL_FLAG == byteBuf.getByte(0)) {
                interceptContext.getConnectionInfo().setHttps(true);
            }
            interceptPipeline.onRequest(interceptContext, msg);
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
