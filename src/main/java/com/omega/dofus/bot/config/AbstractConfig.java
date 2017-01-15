package com.omega.dofus.bot.config;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;

public abstract class AbstractConfig<T extends AbstractConfig> {

    private final File configFile;
    private final AbstractConfigReader reader;
    private final AbstractConfigWriter writer;

    protected AbstractConfig(File configFile) {
        if (configFile == null) {
            throw new NullPointerException("No configuration file provided");
        }

        this.configFile = configFile;

        this.reader = createConfigReader((T) this);
        this.writer = createConfigWriter((T) this);
    }

    protected abstract AbstractConfigReader createConfigReader(T config);

    protected abstract AbstractConfigWriter createConfigWriter(T config);

    public File getConfigFile() {
        return configFile;
    }

    public void read() throws IOException, ParserConfigurationException, SAXException,
            XPathExpressionException, XPathFactoryConfigurationException {
        reader.read();
    }

    public void save()
            throws IOException, TransformerException, ParserConfigurationException {
        writer.save();
    }
}
