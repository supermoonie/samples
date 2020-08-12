package com.github.supermoonie.mitmproxy;

/**
 * @author supermoonie
 * @since 2020/8/9
 */
public class ConnectionInfo {

    private String remoteHost;

    private int remotePort;

    private String clientHost;

    private int clientPort;

    private boolean isHttps = false;

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    public boolean isHttps() {
        return isHttps;
    }

    public void setHttps(boolean https) {
        isHttps = https;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "remoteHost='" + remoteHost + '\'' +
                ", remotePort=" + remotePort +
                ", clientHost='" + clientHost + '\'' +
                ", clientPort=" + clientPort +
                ", isHttps=" + isHttps +
                '}';
    }
}
