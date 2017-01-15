package com.omega.dofus.bot.network.messages.connection;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.BuildInfos;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.types.version.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentificationFailedForBadVersionMessage extends IdentificationFailedMessage {

    public static final int ID = 21;

    private static final Logger LOGGER =
            LoggerFactory.getLogger(IdentificationFailedForBadVersionMessage.class);

    private Version version;

    public IdentificationFailedForBadVersionMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        super.serialize(out);

        out.writeSerializable(version);
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        super.deserialize(in);

        version = new Version();
        version.deserialize(in);
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public void process(BotInstance instance) throws Exception {
        LOGGER.warn("Your version is not up-to-date. Expected : {}, Current : {}", version,
                    BuildInfos.VERSION_EXTENDED);
        instance.disconnect();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("IdentificationFailedForBadVersionMessage[reason: ").append(reason)
                .append(", requiredVersion: ").append(version)
                .append(']');

        return sb.toString();
    }
}
