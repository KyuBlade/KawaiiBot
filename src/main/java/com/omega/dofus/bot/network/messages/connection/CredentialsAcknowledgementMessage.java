package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class CredentialsAcknowledgementMessage extends NetworkMessage {

    public static final int ID = 6314;

    public CredentialsAcknowledgementMessage() {

    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String toString() {
        return "CredentialsAcknowledgmentMessage[]";
    }
}
