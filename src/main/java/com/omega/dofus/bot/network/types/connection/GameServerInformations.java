package com.omega.dofus.bot.network.types.connection;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.io.Serializable;

public class GameServerInformations implements Serializable {

    private int id;
    private int type = -1;
    private byte status = 1;
    private byte completion;
    private boolean isSelectable;
    private byte charactersCount;
    private byte charactersSlots;
    private double date;

    public GameServerInformations() {

    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        throw new Exception("Not implemented yet.");
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        this.id = in.readVarUhShort();
        if (this.id < 0) {
            throw new Error(
                    "Forbidden value (" + this.id + ") on element of GameServerInformations.id.");
        }
        this.type = in.readByte();
        this.status = in.readByte();
        if (this.status < 0) {
            throw new Error("Forbidden value (" + this.status +
                                    ") on element of GameServerInformations.status.");
        }
        this.completion = in.readByte();
        if (this.completion < 0) {
            throw new Error("Forbidden value (" + this.completion +
                                    ") on element of GameServerInformations.completion.");
        }
        this.isSelectable = in.readBoolean();
        this.charactersCount = in.readByte();
        if (this.charactersCount < 0) {
            throw new Error("Forbidden value (" + this.charactersCount +
                                    ") on element of GameServerInformations.charactersCount.");
        }
        this.charactersSlots = in.readByte();
        if (this.charactersSlots < 0) {
            throw new Error("Forbidden value (" + this.charactersSlots +
                                    ") on element of GameServerInformations.charactersSlots.");
        }
        this.date = in.readDouble();
        if (this.date < -9007199254740990d || this.date > 9007199254740990d) {
            throw new Error("Forbidden value (" + this.date +
                                    ") on element of GameServerInformations.date.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GameServerInformations[id: ").append(id)
                .append(", type: ").append(type)
                .append(", status: ").append(status)
                .append(", completion: ").append(completion)
                .append(", isSelectable: ").append(isSelectable)
                .append(", charactersCount: ").append(charactersCount)
                .append(", charactersSlots: ").append(charactersSlots)
                .append(", date: ").append(date)
                .append(']');

        return sb.toString();
    }
}
