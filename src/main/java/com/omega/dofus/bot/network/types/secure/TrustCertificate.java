package com.omega.dofus.bot.network.types.secure;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.io.Serializable;

public class TrustCertificate implements Serializable {

    private int id;
    private String hash;

    public TrustCertificate(int id, String hash) {
        this.id = id;
        this.hash = hash;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        if (id < 0) {
            throw new Error("Forbidden value (" + id + ") on element id.");
        }
        out.writeInt(id);
        out.writeUTF(hash);
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        id = in.readInt();
        if (id < 0) {
            throw new Error("Forbidden value (" + id + ") on element of TrustCertificate.id.");
        }
        hash = in.readUTF();
    }

    public int getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }
}
