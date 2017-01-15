package com.omega.dofus.bot.network.messages.handshake;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class ProtocolRequired extends NetworkMessage {

    public static final int ID = 1;

    private int requiredVersion;
    private int currentVersion;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        requiredVersion = in.readInt();
        if (this.requiredVersion < 0) {
            throw new Error("Forbidden value (" + this.requiredVersion +
                                    ") on element of ProtocolRequired.requiredVersion.");
        }

        currentVersion = in.readInt();
        if (this.currentVersion < 0) {
            throw new Error("Forbidden value (" + this.currentVersion +
                                    ") on element of ProtocolRequired.currentVersion.");
        }
    }

    public int getRequiredVersion() {
        return requiredVersion;
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    @Override
    public String toString() {
        return "ProtocolRequired[requiredVersion: " + requiredVersion + ", currentVersion: " +
                currentVersion + "]";
    }
}