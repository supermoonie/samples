package com.github.supermoonie.proxy;

import io.netty.channel.*;

/**
 * @author supermoonie
 * @date 2020-08-07
 */
public class HexDumpProxyBackendHandler extends ChannelInboundHandlerAdapter {

    private final Channel clientChannel;

    public HexDumpProxyBackendHandler(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        System.out.println("receive from remote");
        clientChannel.writeAndFlush(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        HexDumpProxyFrontendHandler.closeOnFlush(clientChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        HexDumpProxyFrontendHandler.closeOnFlush(ctx.channel());
    }
}
