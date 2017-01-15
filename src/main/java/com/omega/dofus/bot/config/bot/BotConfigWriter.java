package com.omega.dofus.bot.config.bot;

import com.omega.dofus.bot.config.AbstractConfigWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.FileNotFoundException;

import javax.xml.transform.TransformerException;

public class BotConfigWriter extends AbstractConfigWriter<BotConfig> {

    public BotConfigWriter(BotConfig config) {
        super(config);
    }

    @Override
    protected void save(Document document) throws TransformerException, FileNotFoundException {
        Element rootEle = document.createElement("Bot");
        appendEntry(rootEle, "Open", config.isOpen());
        appendEntry(rootEle, "Username", config.getUsername());
        appendEntry(rootEle, "Password", config.getPassword());
        appendEntry(rootEle, "Server", config.getServer());

        document.appendChild(rootEle);
    }
}
