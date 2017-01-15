package com.omega.dofus.bot.network.messages.game.approach;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

public class AuthenticationTicketMessage extends NetworkMessage {

    public static final int ID = 110;

    private String lang;
    private String ticket;

    public AuthenticationTicketMessage(String lang, String ticket) {
        this.lang = lang;
        this.ticket = ticket;
    }

    @Override
    public int getId() {
        return 110;
    }

    @Override
    public void serialize(CustomIoBuffer out) throws Exception {
        out.writeUTF(lang);
        out.writeUTF(ticket);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AuthenticationTicketMessage[lang: ").append(lang)
                .append(", ticket: ").append(ticket)
                .append(']');

        return sb.toString();
    }
}
