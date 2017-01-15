package com.omega.dofus.bot.config.bot;

import com.omega.dofus.bot.config.AbstractConfigReader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BotConfigReader extends AbstractConfigReader<BotConfig> {

    protected BotConfigReader(BotConfig config) {
        super(config);
    }

    @Override
    protected void read(Document document) {
        Node rootNode = document.getDocumentElement();
        if (rootNode.getNodeName().equalsIgnoreCase("Bot")) {
            NodeList children = rootNode.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                switch (child.getNodeName()) {
                    case "Open":
                        config.setOpen(readBool(child));
                        break;
                    case "Username":
                        config.setUsername(readString(child));
                        break;
                    case "Password":
                        config.setPassword(readString(child));
                        break;
                    case "Server":
                        config.setServer((short) readInt(child));
                }
            }
        }
    }
}
