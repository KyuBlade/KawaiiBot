package com.omega.dofus.bot.config.global;

import com.omega.dofus.bot.Constants;
import com.omega.dofus.bot.config.AbstractConfig;
import com.omega.dofus.bot.config.AbstractConfigReader;
import com.omega.dofus.bot.config.AbstractConfigWriter;

import org.apache.mina.proxy.handlers.socks.SocksProxyConstants;

import java.io.File;

public class GlobalConfig extends AbstractConfig<GlobalConfig> {

    private String loginServerHost = "213.248.126.39";
    private int loginServerPort = 5555;

    private long connectionTimeout = 3000L;
    private long connectionRetryDelay = 5000L;
    private int connectionMaxRetry = 5;

    private boolean useProxy = true;
    private String proxyHost = "45.62.229.139";
    private int proxyPort = 1080;
    private byte proxyVersion = SocksProxyConstants.SOCKS_VERSION_5;

    private String lang;

    private GlobalConfig() {
        super(new File(Constants.CONFIG_DIR.getPath() + File.separatorChar + "settings.xml"));
    }

    public static GlobalConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    protected AbstractConfigReader createConfigReader(GlobalConfig config) {
        return new GlobalConfigReader(config);
    }

    @Override
    protected AbstractConfigWriter createConfigWriter(GlobalConfig config) {
        return new GlobalConfigWriter(config);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("loginServerHost = ").append(loginServerHost).append('\n');
        sb.append("loginServerPort = ").append(loginServerPort).append('\n');
        sb.append("useProxy = ").append(useProxy).append('\n');
        sb.append("proxyHost = ").append(proxyHost).append('\n');
        sb.append("proxyPort = ").append(proxyPort).append('\n');
        sb.append("proxyVersion = ").append(proxyVersion).append('\n');
        sb.append("connectionTimeout = ").append(connectionTimeout).append('\n');
        sb.append("connectionMaxRetry = ").append(connectionMaxRetry).append('\n');
        sb.append("connectionRetryDelay = ").append(connectionRetryDelay).append('\n');
        sb.append("lang = ").append(lang).append('\n');

        return sb.toString();
    }

    public String getLoginServerHost() {
        return loginServerHost;
    }

    public void setLoginServerHost(String loginServerHost) {
        this.loginServerHost = loginServerHost;
    }

    public int getLoginServerPort() {
        return loginServerPort;
    }

    public void setLoginServerPort(int loginServerPort) {
        this.loginServerPort = loginServerPort;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public long getConnectionRetryDelay() {
        return connectionRetryDelay;
    }

    public void setConnectionRetryDelay(long connectionRetryDelay) {
        this.connectionRetryDelay = connectionRetryDelay;
    }

    public int getConnectionMaxRetry() {
        return connectionMaxRetry;
    }

    public void setConnectionMaxRetry(int connectionMaxRetry) {
        this.connectionMaxRetry = connectionMaxRetry;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public byte getProxyVersion() {
        return proxyVersion;
    }

    public void setProxyVersion(byte proxyVersion) {
        this.proxyVersion = proxyVersion;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    private static class SingletonHolder {

        private static final GlobalConfig INSTANCE = new GlobalConfig();
    }
}
