package com.github.supermoonie.mitmproxy.constant;

/**
 * @author supermoonie
 * @since 2020/8/9
 */
public enum RemoteConnectionState {

    /**
     * not connection
     */
    NOT_CONNECTION(0),
    /**
     * already handshake with client
     */
    ALREADY_HANDSHAKE_WITH_CLIENT(1),
    /**
     * connected
     */
    CONNECTED(2)
    ;

    private final int code;

    RemoteConnectionState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
