package com.github.supermoonie.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author supermoonie
 * @date 2020-08-07
 */
public class HexDumpProxy {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture f = b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HexDumpProxyInitializer())
                .bind(10802).sync();
        System.out.println("listening on " + 10802);
        f.channel().closeFuture().sync();
    }
}
