package com.github.supermoonie.mitmproxy.intercept;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author supermoonie
 * @since 2020/8/11
 */
public class RealProxyIntercept extends AbstractIntercept {

    private Queue<Object> requestQueue = new LinkedBlockingDeque<>();

    private boolean connectionFlag = false;

    @Override
    public void onActive(InterceptContext ctx) {

    }

    @Override
    public boolean onRequest(InterceptContext context, Object msg) {
        System.out.println("onRequest class: " + msg.getClass().getName() + ", msg: " + msg);
        if (null == context.getRemoteChannel()) {
            Bootstrap b = new Bootstrap();
            b.group(context.getClientChannel().eventLoop())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(HttpClientCodec.class.getName(), new HttpClientCodec());
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    onResponse(context, msg);
                                    context.getClientChannel().writeAndFlush(msg);
                                }
                            });
                        }
                    });
            String remoteHost = context.getConnectionInfo().getRemoteHost();
            int port = context.getConnectionInfo().getRemotePort();
            ChannelFuture f = b.connect(remoteHost, port);
            Channel remoteChannel = f.channel();
            context.setRemoteChannel(remoteChannel);
            f.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    connectionFlag = true;
                    future.channel().writeAndFlush(msg);
                    Object obj = requestQueue.poll();
                    while (null != obj) {
                        future.channel().writeAndFlush(obj);
                        obj = requestQueue.poll();
                    }
                } else {
                    context.getClientChannel().close();
                }
            });
        } else {
            if (connectionFlag) {
                context.getRemoteChannel().writeAndFlush(msg);
            } else {
                requestQueue.add(msg);
            }
        }

        return true;
    }

    @Override
    public void onResponse(InterceptContext ctx, Object msg) {
        ctx.getClientChannel().writeAndFlush(msg);
    }
}
