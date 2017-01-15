package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.network.io.BooleanByteWrapper;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;
import com.omega.dofus.bot.network.types.version.VersionExtended;

import javax.xml.bind.DatatypeConverter;

public class IdentificationMessage extends NetworkMessage {

    public static final int ID = 4;

    private VersionExtended version;
    private String lang;
    private byte[] credentials;
    private short serverId;
    private boolean autoConnect;
    private boolean useCertificate;
    private boolean useLoginToken;
    private long sessionOptionalSalt = 0;
    public int[] failedAttempts;

    public IdentificationMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        byte booleanByte = 0;
        booleanByte = BooleanByteWrapper.setFlag(booleanByte, 0, this.autoConnect);
        booleanByte = BooleanByteWrapper.setFlag(booleanByte, 1, this.useCertificate);
        booleanByte = BooleanByteWrapper.setFlag(booleanByte, 2, this.useLoginToken);
        out.writeByte(booleanByte);

        out.writeSerializable(version);
        out.writeUTF(lang);

        out.writeVarInt(credentials.length);
        out.writeBytes(credentials);

        out.writeShort(serverId);

        if (sessionOptionalSalt < -9007199254740990L ||
                this.sessionOptionalSalt > 9007199254740990L) {
            throw new Error("Forbidden value (" + sessionOptionalSalt +
                                    ") on element sessionOptionalSalt.");
        }
        out.writeVarLong(sessionOptionalSalt);
        if (failedAttempts != null) {
            out.writeShort((short) failedAttempts.length);
            for (int i = 0; i < failedAttempts.length; i++) {
                int failedAttempt = failedAttempts[i];
                if (failedAttempt < 0) {
                    throw new Error("Forbidden value (" + failedAttempt +
                                            ") on element 9 (starting at 1) of failedAttempts.");
                }
                out.writeVarShort((short) failedAttempt);
            }
        } else {
            out.writeShort((short) 0);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdentificationMessage[version: ").append(version.toString())
                .append(", lang: ").append(lang)
                .append(", autoConnect : ").append(autoConnect)
                .append(", userCertificate: ").append(useCertificate)
                .append(", useLoginToken: ").append(useLoginToken)
                .append(", serverId: ").append(serverId)
                .append(", credentials : ")
                .append(DatatypeConverter.printHexBinary(credentials).toLowerCase())
                .append(", sessionOptionalSalt: ").append(sessionOptionalSalt)
                .append(", failedAttempts: ").append(failedAttempts)
                .append(']');

        return sb.toString();
    }

    public void setVersion(VersionExtended version) {
        this.version = version;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setCredentials(byte[] credentials) {
        this.credentials = credentials;
    }

    public void setServerId(short serverId) {
        this.serverId = serverId;
    }

    public void setAutoConnect(boolean autoConnect) {
        this.autoConnect = autoConnect;
    }

    public void setUseCertificate(boolean useCertificate) {
        this.useCertificate = useCertificate;
    }

    public void setUseLoginToken(boolean useLoginToken) {
        this.useLoginToken = useLoginToken;
    }

    public void setSessionOptionalSalt(long sessionOptionalSalt) {
        this.sessionOptionalSalt = sessionOptionalSalt;
    }

    public void setFailedAttempts(int[] failedAttempts) {
        this.failedAttempts = failedAttempts;
    }
}
