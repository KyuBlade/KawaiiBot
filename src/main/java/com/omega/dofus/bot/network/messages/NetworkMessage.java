package com.omega.dofus.bot.network.messages;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.io.Serializable;

public abstract class NetworkMessage implements Serializable {

    public static final byte BIT_RIGHT_SHIFT_LEN_PACKET_ID = 2;
    public static final byte BIT_MASK = 3;

    public NetworkMessage() {

    }

    public abstract int getId();

    public void serialize(CustomIoBuffer out) throws Exception {
        throw new RuntimeException("Serialize method not implemented");
    }

    public void deserialize(CustomIoBuffer in) throws Exception {
        throw new RuntimeException("Deserialize method not implemented");
    }

    public void read(CustomIoBuffer in) throws Exception {
        deserialize(in);
    }

    public void process(BotInstance instance) throws Exception {

    }
}
