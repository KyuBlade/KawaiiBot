package com.omega.dofus.bot.network.messages.security;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

public class RawDataMessage extends NetworkMessage {

    public static final int ID = 6253;

    private static final Logger LOGGER = LoggerFactory.getLogger(RawDataMessage.class);

    private byte[] content;

    public RawDataMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        int contentLength = in.readVarInt();
        this.content = in.readBytes(contentLength);
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        File rawDataFile = new File("RawDataMessage.swf");
        try (FileOutputStream fos = new FileOutputStream(rawDataFile)) {
            fos.write(content);
        }

        // TODO : Handle the raw data message
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RawDataMessage[content: byte[").append(content.length)
                .append("]]");

        return sb.toString();
    }
}
