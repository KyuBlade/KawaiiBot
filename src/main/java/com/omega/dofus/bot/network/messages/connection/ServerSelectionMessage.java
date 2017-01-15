package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class ServerSelectionMessage extends NetworkMessage {

    public static final int ID = 40;

    private short serverId;

    public ServerSelectionMessage(short serverId) {
        this.serverId = serverId;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        if (this.serverId < 0) {
            throw new Error("Forbidden value (" + this.serverId + ") on element serverId.");
        }
        out.writeVarShort(this.serverId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServerSelectionMessage[serverid: ").append(serverId).append(']');

        return sb.toString();
    }
}
