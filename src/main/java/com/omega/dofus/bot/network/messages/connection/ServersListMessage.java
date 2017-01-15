package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;
import com.omega.dofus.bot.network.types.connection.GameServerInformations;

import java.util.Arrays;

public class ServersListMessage extends NetworkMessage {

    public static final int ID = 30;

    private GameServerInformations[] servers;
    private int alreadyConnectedToServerId;
    private boolean canCreateNewCharacter;

    public ServersListMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        int serversLength = in.readUnsignedShort();
        this.servers = new GameServerInformations[serversLength];
        for (int i = 0; i < serversLength; i++) {
            GameServerInformations server = new GameServerInformations();
            server.deserialize(in);
            this.servers[i] = server;
        }

        this.alreadyConnectedToServerId = in.readVarUhShort();
        if (this.alreadyConnectedToServerId < 0) {
            throw new Error("Forbidden value (" + this.alreadyConnectedToServerId +
                                    ") on element of ServersListMessage.alreadyConnectedToServerId.");
        }

        this.canCreateNewCharacter = in.readBoolean();
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        ServerSelectionMessage ssMessage =
                new ServerSelectionMessage(instance.getConfig().getServer());

        instance.getClient().send(ssMessage);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServersListMessage[")
                .append("servers: ").append(Arrays.toString(servers))
                .append(", alreadyConnectedToServerId: ").append(alreadyConnectedToServerId)
                .append(", canCreateNewCharacter: ").append(canCreateNewCharacter)
                .append(']');

        return sb.toString();
    }
}
