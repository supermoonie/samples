package com.github.supermoonie.mitmproxy;

import com.github.supermoonie.mitmproxy.codec.MitmProxyCodec;
import com.github.supermoonie.mitmproxy.ex.MitmProxyStartException;
import com.github.supermoonie.mitmproxy.ex.MtimProxyCloseException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author supermoonie
 * @date 2020-08-08
 */
public class MitmProxy {

    private final NioEventLoopGroup boss;

    private final NioEventLoopGroup worker;

    private final int port;

    private ChannelFuture future;

    public MitmProxy(int nBoosThread, int nWorkerThread, int port) {
        this.boss = new NioEventLoopGroup(nBoosThread);
        this.worker = new NioEventLoopGroup(nWorkerThread);
        this.port = port;
    }

    public static void main(String[] args) {
        new MitmProxy(1, 5, 10800).start().close();
    }

    public MitmProxy start() {
        ServerBootstrap b = new ServerBootstrap();
        try {
            future = b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(HttpServerCodec.class.getSimpleName(), new HttpServerCodec());
                            ch.pipeline().addLast(MitmProxyCodec.class.getSimpleName(), new MitmProxyCodec());
                        }
                    }).bind(port).sync();
            return this;
        } catch (InterruptedException e) {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
            throw new MitmProxyStartException(e);
        }
    }

    public void close() {
        if (null != future && future.channel().isOpen()) {
            try {
                future.channel().close().sync();
            } catch (InterruptedException e) {
                throw new MtimProxyCloseException(e);
            } finally {
                worker.shutdownGracefully();
                boss.shutdownGracefully();
            }
        }
    }

}
