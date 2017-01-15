package com.omega.dofus.bot;

import org.apache.mina.proxy.handlers.socks.SocksProxyConstants;

public class Config {

    public static final String LOGIN_SERVER_HOST = "213.248.126.39";
    public static final int LOGIN_SERVER_PORT = 5555;

    /**
     * In milliseconds.
     */
    public static final long CONNECTION_TIMEOUT = 3000L;

    /**
     * In milliseconds.
     */
    public static final long CONNECTION_RETRY_DELAY = 5000L;

    /**
     * Number of connection retry before stopping
     */
    public static final int CONNECTON_MAX_RETRY = 5;

    public static final boolean USE_PROXY = true;
    public static final String PROXY_HOST = "45.62.229.139";
    public static final int PROXY_PORT = 1080;
    public static final byte PROXY_VERSION = SocksProxyConstants.SOCKS_VERSION_5;

    public static final String LANG_CURRENT = "fr";
}
