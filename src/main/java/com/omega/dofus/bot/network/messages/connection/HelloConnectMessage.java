package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.logic.connection.managers.AuthentificationManager;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class HelloConnectMessage extends NetworkMessage {

    public static final int ID = 3;

    private String salt;
    private byte[] key;

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String toString() {
        return "HelloConnectMessage[salt: " + salt + ", key: byte[" + key.length + "]]";
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        out.writeUTF(salt);
        int keyLength = key.length;
        out.writeVarInt(keyLength);
        for (int i = 0; i < keyLength; i++) {
            out.writeByte(key[i]);
        }
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        salt = in.readUTF();
        int keysLength = in.readVarInt();
        key = new byte[keysLength];
        for (int i = 0; i < keysLength; i++) {
            key[i] = in.readByte();
        }
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        AuthentificationManager authManager = instance.getAuthentificationManager();
        authManager.setPublicKey(key);
        authManager.setSalt(salt);
        authManager.initAESKey();
        IdentificationMessage idMessage = authManager.getIdentificationMessage();

        instance.getClient().send(idMessage);
    }

    public String getSalt() {
        return salt;
    }

    public byte[] getKey() {
        return key;
    }
}
