package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.logic.connection.managers.AuthentificationManager;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class SelectedServerDataMessage extends NetworkMessage {

    public static final int ID = 42;

    private int serverId;
    private String address;
    private int port;
    private boolean canCreateNewCharacter;
    private byte[] ticket;

    public SelectedServerDataMessage() {

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
                                    ") on element of SelectedServerDataMessage.serverId.");
        }

        this.address = in.readUTF();
        this.port = in.readUnsignedShort();
        if (this.port < 0 || this.port > 65535) {
            throw new Error("Forbidden value (" + this.port +
                                    ") on element of SelectedServerDataMessage.port.");
        }

        this.canCreateNewCharacter = in.readBoolean();
        int ticketLength = in.readVarInt();
        this.ticket = new byte[ticketLength];
        for (int i = 0; i < ticketLength; i++) {
            this.ticket[i] = in.readByte();
        }
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        AuthentificationManager authManager = instance.getAuthentificationManager();
        authManager.setGameServerTicket(ticket);
        instance.switchToGameServer(address, port);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SelectedServerDataMessage[serverId: ").append(serverId)
                .append(", address: ").append(address)
                .append(", port: ").append(port)
                .append(", canCreateNewCharacter: ").append(canCreateNewCharacter)
                .append(", ticket: ").append(ticket)
                .append(']');

        return sb.toString();
    }
}
