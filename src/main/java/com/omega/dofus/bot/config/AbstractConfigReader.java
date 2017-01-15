package com.omega.dofus.bot.config;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

public abstract class AbstractConfigReader<T extends AbstractConfig> {

    protected final T config;
    protected final XPathFactory pathFactory;

    protected AbstractConfigReader(T config) {
        this.config = config;
        this.pathFactory = XPathFactory.newInstance();

    }

    public final void read() throws ParserConfigurationException, IOException, SAXException,
            XPathFactoryConfigurationException, XPathExpressionException {
        if (!config.getConfigFile().exists()) {
            throw new FileNotFoundException(
                    "Configuration file " + config.getConfigFile().getName() + " not found");
        }
        if (!config.getConfigFile().isFile()) {
            throw new FileNotFoundException(
                    "Configuration file " + config.getConfigFile().getName() +
                            " should not be a directory");
        }

        Document
                document =
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(config.getConfigFile());
        read(document);
    }

    protected abstract void read(Document document)
            throws XPathFactoryConfigurationException, XPathExpressionException;

    protected String readString(Node node) {
        return node.getTextContent();
    }

    protected int readInt(Node node) {
        String text = node.getTextContent();
        return Integer.parseInt(text);
    }

    protected long readLong(Node node) {
        String text = node.getTextContent();
        return Long.parseLong(text);
    }

    protected boolean readBool(Node node) {
        String text = node.getTextContent();
        return Boolean.parseBoolean(text);
    }
}