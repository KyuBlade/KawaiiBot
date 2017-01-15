package com.omega.dofus.bot.network.messages.queue;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class QueueStatusMessage extends NetworkMessage {

    public static final int ID = 6100;

    private int position;
    private int total;

    public QueueStatusMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        this.position = in.readUnsignedShort();
        if (this.position < 0 || this.position > 65535) {
            throw new Error("Forbidden value (" + this.position +
                                    ") on element of QueueStatusMessage.position.");
        }

        this.total = in.readUnsignedShort();
        if (this.total < 0 || this.total > 65535) {
            throw new Error("Forbidden value (" + this.total +
                                    ") on element of QueueStatusMessage.total.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("LoginQueueStatusMessage[")
                .append("position: ").append(position)
                .append(", total: ").append(total)
                .append(']');

        return sb.toString();
    }

    public int getPosition() {
        return position;
    }

    public int getTotal() {
        return total;
    }
}
