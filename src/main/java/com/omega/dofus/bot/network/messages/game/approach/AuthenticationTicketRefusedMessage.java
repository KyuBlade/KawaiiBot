package com.omega.dofus.bot.network.messages.game.approach;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationTicketRefusedMessage extends NetworkMessage {

    public static final int ID = 112;
    private static final Logger LOGGER =
            LoggerFactory.getLogger(AuthenticationTicketRefusedMessage.class);

    public AuthenticationTicketRefusedMessage() {

    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {

    }

    @Override
    public void process(BotInstance instance) throws Exception {
        LOGGER.error("Game ticket refused");
        instance.disconnect();
    }

    @Override
    public String toString() {
        return "AuthenticationTicketRefusedMessage[]";
    }
}
