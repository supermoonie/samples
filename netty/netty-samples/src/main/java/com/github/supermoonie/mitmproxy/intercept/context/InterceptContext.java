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

    public InterceptContext(Channel clientChannel, Object requestMsg, ConnectionInfo currentConnectionInfo) {
        this.clientChannel = clientChannel;
        this.requestMsg = requestMsg;
        this.currentConnectionInfo = currentConnectionInfo;
    }

    public InterceptContext(Channel clientChannel, Channel remoteChannel, Object requestMsg, ConnectionInfo currentConnectionInfo, Object responseMsg) {
        this.clientChannel = clientChannel;
        this.remoteChannel = remoteChannel;
        this.requestMsg = requestMsg;
        this.currentConnectionInfo = currentConnectionInfo;
        this.responseMsg = responseMsg;
    }

    private Channel clientChannel;

    private Channel remoteChannel;

    private Object requestMsg;

    private ConnectionInfo currentConnectionInfo;

    private Object responseMsg;

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

    public Object getRequestMsg() {
        return requestMsg;
    }

    public void setRequestMsg(Object requestMsg) {
        this.requestMsg = requestMsg;
    }

    public ConnectionInfo getCurrentConnectionInfo() {
        return currentConnectionInfo;
    }

    public void setCurrentConnectionInfo(ConnectionInfo currentConnectionInfo) {
        this.currentConnectionInfo = currentConnectionInfo;
    }

    public Object getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(Object responseMsg) {
        this.responseMsg = responseMsg;
    }
}
