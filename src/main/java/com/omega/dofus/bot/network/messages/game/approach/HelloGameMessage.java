package com.omega.dofus.bot.network.messages.game.approach;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.config.global.GlobalConfig;
import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class HelloGameMessage extends NetworkMessage {

    public static final int ID = 101;

    public HelloGameMessage() {

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
        String lang = GlobalConfig.getInstance().getLang();
        String ticket = instance.getAuthentificationManager().getGameServerTicket();
        AuthenticationTicketMessage atMessage = new AuthenticationTicketMessage(lang, ticket);

        instance.getClient().send(atMessage);
    }

    @Override
    public String toString() {
        return "HelloGameMessage[]";
    }
}
