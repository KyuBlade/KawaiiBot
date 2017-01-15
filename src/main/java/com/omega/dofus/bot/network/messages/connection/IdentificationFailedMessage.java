
package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.enums.IdentificationFailureReasonEnum;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentificationFailedMessage extends NetworkMessage {

    public static final int ID = 20;

    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificationFailedMessage.class);

    protected byte reason;

    public IdentificationFailedMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        out.writeByte(reason);
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        reason = in.readByte();
        if (this.reason < 0) {
            throw new Error("Forbidden value (" + this.reason +
                                    ") on element of IdentificationFailedMessage.reason.");
        }
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        LOGGER.info("Identification failed. Cause : {}",
                    IdentificationFailureReasonEnum.valueOf(reason));
        instance.disconnect();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdentificationFailedMessage[reason: ").append(reason).append(']');

        return sb.toString();
    }

    public byte getReason() {
        return reason;
    }
}
