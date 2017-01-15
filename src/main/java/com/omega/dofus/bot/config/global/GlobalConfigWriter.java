package com.omega.dofus.bot.config.global;

import com.omega.dofus.bot.config.AbstractConfigWriter;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileNotFoundException;

import javax.xml.transform.TransformerException;

public class GlobalConfigWriter extends AbstractConfigWriter<GlobalConfig> {

    public GlobalConfigWriter(GlobalConfig config) {
        super(config);
    }

    @Override
    protected void save(Document document)
            throws TransformerException, FileNotFoundException {
        Element rootEle = document.createElement("Config");

        // Connection
        Element connectionEle = document.createElement("Connection");

        // Login Server
        Element loginServerEle = document.createElement("LoginServer");
        appendEntry(loginServerEle, "Host", config.getLoginServerHost());
        appendEntry(loginServerEle, "Port", config.getLoginServerPort());
        connectionEle.appendChild(loginServerEle);

        // Proxy
        Element proxyElement = document.createElement("Proxy");
        appendEntry(proxyElement, "UseProxy", config.isUseProxy());
        appendEntry(proxyElement, "Host", config.getProxyHost());
        appendEntry(proxyElement, "Port", config.getProxyPort());
        appendComment(proxyElement, "Socks version");
        appendEntry(proxyElement, "Version", config.getProxyVersion());
        connectionEle.appendChild(proxyElement);

        appendComment(connectionEle, "In milliseconds");
        appendEntry(connectionEle, "Timeout", config.getConnectionTimeout());
        appendEntry(connectionEle, "MaxRetry", config.getConnectionMaxRetry());
        appendComment(connectionEle, "In milliseconds");
        appendEntry(connectionEle, "RetryDelay", config.getConnectionRetryDelay());

        rootEle.appendChild(connectionEle);

        appendEntry(rootEle, "Lang", config.getLang());

        document.appendChild(rootEle);
    }
}
