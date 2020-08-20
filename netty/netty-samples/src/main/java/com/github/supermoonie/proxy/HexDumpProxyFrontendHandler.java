package com.github.supermoonie.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author supermoonie
 * @date 2020-08-07
 */
public class HexDumpProxyFrontendHandler extends ChannelInboundHandlerAdapter {

    private static final Pattern HOST_PORT_PATTERN = Pattern.compile("^(?:https?://)?(?<host>[^:]*)(?::(?<port>\\d+))?(/.*)?$");

    private String remoteHost;

    private int remotePort;

    private int status = 0;

    private Channel remoteChannel;

    private Queue<Object> queue = new LinkedBlockingDeque<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel clientChannel = ctx.channel();
        InetSocketAddress clientAddress = (InetSocketAddress) clientChannel.remoteAddress();
        System.out.println(clientAddress.getHostString() + ":" + clientAddress.getPort() + " connected");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("class: " + msg.getClass().getName() + ", msg: " + msg);
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("remote: " + request.uri() + ", method: " + request.method());
            remoteHost = request.headers().get(HttpHeaderNames.HOST);
//            Matcher matcher = HOST_PORT_PATTERN.matcher(request.uri());
//            if (matcher.find()) {
//                remotePort = Integer.parseInt(matcher.group("port"));
//            }
            remotePort = 443;
            if (status == 0) {
                Channel clientChannel = ctx.channel();
                Bootstrap b = new Bootstrap();
                b.group(clientChannel.eventLoop())
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel ch) throws Exception {
                                ch.pipeline().addLast("httpCodec", new HttpClientCodec());
                                ch.pipeline().addLast(new HexDumpProxyBackendHandler(clientChannel));
                            }
                        });
                System.out.println("connect " + remoteHost + ":" + remotePort);
                ChannelFuture f = b.connect("httpbin.org", remotePort);
                remoteChannel = f.channel();
                f.addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        // connection complete start to read first data
//                        clientChannel.read();
                        System.out.println("send msg to remote ..., class: " + msg.getClass().getName() + " " + msg);
                        future.channel().writeAndFlush(msg);
                        Object obj = queue.poll();
                        while (null != obj) {
                            System.out.println("send msg to remote from queue ... class: " + obj.getClass().getName() + " " + obj);
                            future.channel().writeAndFlush(obj);
                            obj = queue.poll();
                        }
                    } else {
                        // Close the connection if the connection attempt has failed.
                        clientChannel.close();
                    }
                });
                status = 1;
            }
        } else {
            queue.add(msg);
//            ReferenceCountUtil.release(msg);
//            if (null != remoteChannel && remoteChannel.isActive()) {
//                System.out.println("send msg to remote");
//                remoteChannel.writeAndFlush(msg);
//            } else {
//                System.out.println("add msg to queue");
//                queue.add(msg);
//            }
        }
        if (1 == status) {
//            InetSocketAddress remoteAddress = (InetSocketAddress) remoteChannel.remoteAddress();
//            System.out.println("send msg to " + remoteAddress.getHostString() + ":" + remoteAddress.getPort());
//            System.out.println("send msg to remote");
//            remoteChannel.writeAndFlush(msg)
//                    .addListener((ChannelFutureListener) future -> {
//                if (future.isSuccess()) {
//                    // was able to flush out data, start to read the next chunk
//                    ctx.channel().read();
//                } else {
//                    future.channel().close();
//                }
//            })
            ;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("inactive");
        if (remoteChannel != null) {
            closeOnFlush(remoteChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
