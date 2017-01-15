package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.network.io.BooleanByteWrapper;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class IdentificationSuccessMessage extends NetworkMessage {

    public static final int ID = 22;

    private String login;
    private String nickname;
    private int accountId;
    private byte communityId;
    private boolean hasRights;
    private String secretQuestion;
    private double accountCreation;
    private double subscriptionElapsedDuration;
    private double subscriptionEndDate;
    private boolean wasAlreadyConnected;
    private short havenbagAvailableRoom;

    public IdentificationSuccessMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        byte booleanByte = 0;
        booleanByte = BooleanByteWrapper.setFlag(booleanByte, 0, this.hasRights);
        booleanByte = BooleanByteWrapper.setFlag(booleanByte, 1, this.wasAlreadyConnected);
        out.writeByte(booleanByte);

        out.writeUTF(this.login);
        out.writeUTF(this.nickname);

        if (this.accountId < 0) {
            throw new Error("Forbidden value (" + this.accountId + ") on element accountId.");
        }
        out.writeInt(this.accountId);

        if (this.communityId < 0) {
            throw new Error("Forbidden value (" + this.communityId + ") on element communityId.");
        }
        out.writeByte(this.communityId);
        out.writeUTF(this.secretQuestion);
        if (this.accountCreation < 0 || this.accountCreation > 9007199254740990d) {
            throw new Error(
                    "Forbidden value (" + this.accountCreation + ") on element accountCreation.");
        }
        out.writeDouble(this.accountCreation);
        if (this.subscriptionElapsedDuration < 0 ||
                this.subscriptionElapsedDuration > 9007199254740990d) {
            throw new Error("Forbidden value (" + this.subscriptionElapsedDuration +
                                    ") on element subscriptionElapsedDuration.");
        }
        out.writeDouble(this.subscriptionElapsedDuration);
        if (this.subscriptionEndDate < 0 || this.subscriptionEndDate > 9007199254740990d) {
            throw new Error("Forbidden value (" + this.subscriptionEndDate +
                                    ") on element subscriptionEndDate.");
        }
        out.writeDouble(this.subscriptionEndDate);
        if (this.havenbagAvailableRoom < 0 || this.havenbagAvailableRoom > 255) {
            throw new Error("Forbidden value (" + this.havenbagAvailableRoom +
                                    ") on element havenbagAvailableRoom.");
        }
        out.writeByte(this.havenbagAvailableRoom);
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        byte booleanByte = in.readByte();
        this.hasRights = BooleanByteWrapper.getFlag(booleanByte, 0);
        this.wasAlreadyConnected = BooleanByteWrapper.getFlag(booleanByte, 1);

        this.login = in.readUTF();
        this.nickname = in.readUTF();

        this.accountId = in.readInt();
        if (this.accountId < 0) {
            throw new Error("Forbidden value (" + this.accountId +
                                    ") on element of IdentificationSuccessMessage.accountId.");
        }

        this.communityId = in.readByte();
        if (this.communityId < 0) {
            throw new Error("Forbidden value (" + this.communityId +
                                    ") on element of IdentificationSuccessMessage.communityId.");
        }

        this.secretQuestion = in.readUTF();
        this.accountCreation = in.readDouble();
        if (this.accountCreation < 0 || this.accountCreation > 9007199254740990d) {
            throw new Error("Forbidden value (" + this.accountCreation +
                                    ") on element of IdentificationSuccessMessage.accountCreation.");
        }

        this.subscriptionElapsedDuration = in.readDouble();
        if (this.subscriptionElapsedDuration < 0 ||
                this.subscriptionElapsedDuration > 9007199254740990d) {
            throw new Error("Forbidden value (" + this.subscriptionElapsedDuration +
                                    ") on element of IdentificationSuccessMessage.subscriptionElapsedDuration.");
        }

        this.subscriptionEndDate = in.readDouble();
        if (this.subscriptionEndDate < 0 || this.subscriptionEndDate > 9007199254740990d) {
            throw new Error("Forbidden value (" + this.subscriptionEndDate +
                                    ") on element of IdentificationSuccessMessage.subscriptionEndDate.");
        }

        this.havenbagAvailableRoom = in.readUnsignedByte();
        if (this.havenbagAvailableRoom < 0 || this.havenbagAvailableRoom > 255) {
            throw new Error("Forbidden value (" + this.havenbagAvailableRoom +
                                    ") on element of IdentificationSuccessMessage.havenbagAvailableRoom.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdentificationSuccessMessage{")
                .append("login: ").append(login)
                .append(", nickname: ").append(nickname)
                .append(", accountId: ").append(accountId)
                .append(", communityId: ").append(communityId)
                .append(", hasRights: ").append(hasRights)
                .append(", wasAlreadyConnected: ").append(wasAlreadyConnected)
                .append(", secretQuestion: ").append(secretQuestion)
                .append(", accountCreation: ").append(accountCreation)
                .append(", subscriptionElapsedDuration: ").append(subscriptionElapsedDuration)
                .append(", subscriptionEndDate: ").append(subscriptionEndDate)
                .append(", havenbagAvailableRoom: ").append(havenbagAvailableRoom)
                .append(']');

        return sb.toString();
    }
}
