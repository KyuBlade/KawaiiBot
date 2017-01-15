package com.omega.dofus.bot.config.global;

import com.omega.dofus.bot.config.AbstractConfigReader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

public class GlobalConfigReader extends AbstractConfigReader<GlobalConfig> {

    public GlobalConfigReader(GlobalConfig config) {
        super(config);
    }

    @Override
    protected void read(Document document)
            throws XPathFactoryConfigurationException, XPathExpressionException {
        Node rootNode = document.getDocumentElement();
        if (rootNode.getNodeName().equalsIgnoreCase("Config")) {
            NodeList children = rootNode.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                switch (child.getNodeName()) {
                    case "Connection":
                        readConnectionNode(child);
                        break;
                    case "Lang":
                        config.setLang(readString(child));
                        break;
                }
            }
        }
    }

    private void readConnectionNode(Node connectionNode) {
        NodeList children = connectionNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getNodeName()) {
                case "LoginServer":
                    readLoginServerNode(child);
                    break;
                case "Proxy":
                    readProxyNode(child);
                    break;
                case "Timeout":
                    config.setConnectionTimeout(readLong(child));
                    break;
                case "MaxRetry":
                    config.setConnectionMaxRetry(readInt(child));
                    break;
                case "RetryDelay":
                    config.setConnectionRetryDelay(readLong(child));
                    break;
            }
        }
    }

    private void readLoginServerNode(Node loginServerNode) {
        NodeList children = loginServerNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getNodeName()) {
                case "Host":
                    config.setLoginServerHost(readString(child));
                    break;
                case "Port":
                    config.setLoginServerPort(readInt(child));
                    break;
            }
        }
    }

    private void readProxyNode(Node proxyNode) throws IllegalArgumentException {
        NodeList children = proxyNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getNodeName()) {
                case "UseProxy":
                    config.setUseProxy(readBool(child));
                    break;
                case "Host":
                    config.setProxyHost(readString(child));
                    break;
                case "Port":
                    config.setProxyPort(readInt(child));
                    break;
                case "Version":
                    int socksVersion = readInt(child);
                    if (socksVersion != 4 && socksVersion != 5) {
                        throw new IllegalArgumentException(
                                "Wrong proxy socks version provided, " + socksVersion +
                                        ", supported, 4 and 5");
                    }
                    config.setProxyVersion((byte) socksVersion);
                    break;
            }
        }
    }
}
