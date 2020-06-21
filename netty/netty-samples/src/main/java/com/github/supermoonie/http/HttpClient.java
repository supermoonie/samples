package com.github.supermoonie.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * @author supermoonie
 * @date 2020-06-08
 */
public class HttpClient {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup workGroup = new NioEventLoopGroup(2);
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .option(ChannelOption.AUTO_CLOSE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ReadTimeoutHandler(10));
                        ch.pipeline().addLast(new HttpClientCodec());
//                        ch.pipeline().addLast(new WriteTimeoutHandler(1));
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpContent>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) {
                                byte[] bytes = new byte[msg.content().readableBytes()];
                                msg.content().readBytes(bytes);
                                System.out.println(new String(bytes, StandardCharsets.UTF_8));
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                cause.printStackTrace();
                            }
                        });
                    }
                });
        ChannelFuture f = bootstrap.connect("127.0.0.1", 8080);
        URI uri = new URI("http://127.0.0.1:8080");
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
        // 发送http请求
        f.channel().writeAndFlush(request);
        f.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
    }
}
