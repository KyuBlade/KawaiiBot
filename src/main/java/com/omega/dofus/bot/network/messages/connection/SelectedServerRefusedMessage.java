package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.enums.ServerConnectionErrorEnum;
import com.omega.dofus.bot.network.enums.ServerStatusEnum;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectedServerRefusedMessage extends NetworkMessage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SelectedServerRefusedMessage.class);

    public static final int ID = 41;

    private int serverId;
    private byte error = 1;
    private byte serverStatus = 1;

    public SelectedServerRefusedMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        this.serverId = in.readVarUhShort();
        if (this.serverId < 0) {
            throw new Error("Forbidden value (" + this.serverId +
                                    ") on element of SelectedServerRefusedMessage.serverId.");
        }
        this.error = in.readByte();
        if (this.error < 0) {
            throw new Error("Forbidden value (" + this.error +
                                    ") on element of SelectedServerRefusedMessage.error.");
        }
        this.serverStatus = in.readByte();
        if (this.serverStatus < 0) {
            throw new Error("Forbidden value (" + this.serverStatus +
                                    ") on element of SelectedServerRefusedMessage.serverStatus.");
        }
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        LOGGER.warn("Unable to connect to the server {}, Error : {}, Status : {}", serverId,
                    ServerConnectionErrorEnum.values()[error],
                    ServerStatusEnum.values()[serverStatus]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SelectedServerRefusedMessage[serverId: ").append(serverId)
                .append(", error: ").append(error)
                .append(", serverStatus: ").append(serverStatus)
                .append("]");

        return sb.toString();
    }
}
