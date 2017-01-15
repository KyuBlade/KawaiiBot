package com.omega.dofus.bot.config;

import com.omega.dofus.bot.Constants;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public abstract class AbstractConfigWriter<T extends AbstractConfig> {

    protected final T config;

    protected AbstractConfigWriter(T config) {
        this.config = config;
    }

    public final void save()
            throws ParserConfigurationException, TransformerException, IOException {
        if (!Constants.CONFIG_DIR.exists()) {
            Constants.CONFIG_DIR.mkdirs();
        }
        if (!config.getConfigFile().exists()) {
            config.getConfigFile().createNewFile();
        }

        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        save(document);

        Transformer transf = TransformerFactory.newInstance().newTransformer();
        transf.setOutputProperty(OutputKeys.INDENT, "yes");
        transf.setOutputProperty(OutputKeys.METHOD, "xml");
        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transf.transform(new DOMSource(document),
                         new StreamResult(new FileOutputStream(config.getConfigFile())));
    }

    protected abstract void save(Document document)
            throws TransformerException, FileNotFoundException;

    protected void appendEntry(Element root, String entryName, Object value) {
        Element element = root.getOwnerDocument().createElement(entryName);
        element.setTextContent(value == null ? "" : value.toString());
        root.appendChild(element);
    }

    protected void appendComment(Element root, String text) {
        Comment comment = root.getOwnerDocument().createComment(text);
        root.appendChild(comment);
    }
}