package com.github.supermoonie.mitmproxy.intercept;

import com.github.supermoonie.mitmproxy.ConnectionInfo;

import java.util.List;
import java.util.Map;

/**
 * @author supermoonie
 * @since 2020/8/15
 */
public class ConfigurableIntercept  extends AbstractIntercept {

    private List<String> blackList;

    private List<String> whiteList;

    private Map<String, String> remoteMap;

    private Map<String, String> localMap;

    private String userName;

    private String password;

    @Override
    public boolean onRequest(InterceptContext ctx, Object msg) {
        ConnectionInfo connectionInfo = ctx.getConnectionInfo();
        return super.onRequest(ctx, msg);
    }
}
