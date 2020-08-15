package com.github.supermoonie.mitmproxy.intercept.context;

import com.github.supermoonie.mitmproxy.ConnectionInfo;
import io.netty.channel.Channel;

/**
 * @author supermoonie
 * @since 2020/8/12
 */
public class InterceptContext {

    public InterceptContext() {
    }

    public InterceptContext(Channel clientChannel, ConnectionInfo currentConnectionInfo) {
        this.clientChannel = clientChannel;
        this.currentConnectionInfo = currentConnectionInfo;
    }

    public InterceptContext(Channel clientChannel, Channel remoteChannel, ConnectionInfo currentConnectionInfo) {
        this.clientChannel = clientChannel;
        this.remoteChannel = remoteChannel;
        this.currentConnectionInfo = currentConnectionInfo;
    }

    private Channel clientChannel;

    private Channel remoteChannel;

    private ConnectionInfo currentConnectionInfo;

    public Channel getClientChannel() {
        return clientChannel;
    }

    public void setClientChannel(Channel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public Channel getRemoteChannel() {
        return remoteChannel;
    }

    public void setRemoteChannel(Channel remoteChannel) {
        this.remoteChannel = remoteChannel;
    }

    public ConnectionInfo getCurrentConnectionInfo() {
        return currentConnectionInfo;
    }

    public void setCurrentConnectionInfo(ConnectionInfo currentConnectionInfo) {
        this.currentConnectionInfo = currentConnectionInfo;
    }
}
