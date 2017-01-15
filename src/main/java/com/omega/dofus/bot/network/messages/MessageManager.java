package com.omega.dofus.bot.network.messages;

import com.omega.dofus.bot.network.messages.connection.CredentialsAcknowledgementMessage;
import com.omega.dofus.bot.network.messages.connection.HelloConnectMessage;
import com.omega.dofus.bot.network.messages.connection.IdentificationFailedForBadVersionMessage;
import com.omega.dofus.bot.network.messages.connection.IdentificationFailedMessage;
import com.omega.dofus.bot.network.messages.connection.IdentificationMessage;
import com.omega.dofus.bot.network.messages.connection.IdentificationSuccessMessage;
import com.omega.dofus.bot.network.messages.connection.SelectedServerDataMessage;
import com.omega.dofus.bot.network.messages.connection.SelectedServerRefusedMessage;
import com.omega.dofus.bot.network.messages.connection.ServerSelectionMessage;
import com.omega.dofus.bot.network.messages.connection.ServersListMessage;
import com.omega.dofus.bot.network.messages.game.approach.AuthenticationTicketMessage;
import com.omega.dofus.bot.network.messages.game.approach.AuthenticationTicketRefusedMessage;
import com.omega.dofus.bot.network.messages.game.approach.HelloGameMessage;
import com.omega.dofus.bot.network.messages.handshake.ProtocolRequired;
import com.omega.dofus.bot.network.messages.queue.LoginQueueStatusMessage;
import com.omega.dofus.bot.network.messages.queue.QueueStatusMessage;
import com.omega.dofus.bot.network.messages.security.RawDataMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private Map<Integer, Class<? extends NetworkMessage>> messages = new HashMap<>();

    private MessageManager() {

    }

    public void registerMessages() {
        if (messages.isEmpty()) {
            registerMessage(ProtocolRequired.ID, ProtocolRequired.class);
            registerMessage(HelloConnectMessage.ID, HelloConnectMessage.class);
            registerMessage(IdentificationMessage.ID, IdentificationMessage.class);
            registerMessage(LoginQueueStatusMessage.ID, LoginQueueStatusMessage.class);
            registerMessage(QueueStatusMessage.ID, QueueStatusMessage.class);
            registerMessage(IdentificationFailedMessage.ID, IdentificationFailedMessage.class);
            registerMessage(IdentificationFailedForBadVersionMessage.ID,
                            IdentificationFailedForBadVersionMessage.class);
            registerMessage(CredentialsAcknowledgementMessage.ID,
                            CredentialsAcknowledgementMessage.class);
            registerMessage(IdentificationSuccessMessage.ID, IdentificationSuccessMessage.class);
            registerMessage(ServersListMessage.ID, ServersListMessage.class);
            registerMessage(ServerSelectionMessage.ID, ServerSelectionMessage.class);
            registerMessage(SelectedServerRefusedMessage.ID, SelectedServerRefusedMessage.class);
            registerMessage(SelectedServerDataMessage.ID, SelectedServerDataMessage.class);
            registerMessage(HelloGameMessage.ID, HelloGameMessage.class);
            registerMessage(AuthenticationTicketMessage.ID, AuthenticationTicketMessage.class);
            registerMessage(AuthenticationTicketRefusedMessage.ID,
                            AuthenticationTicketRefusedMessage.class);
            registerMessage(RawDataMessage.ID, RawDataMessage.class);
        } else {
            throw new IllegalStateException("Messages have already been registered!");
        }
    }

    private void registerMessage(int id, Class<? extends NetworkMessage> type) {
        messages.put(id, type);
    }

    public Class<? extends NetworkMessage> getMessage(int id) {
        return messages.get(id);
    }

    public NetworkMessage newMessage(int id) throws Exception {
        Class<? extends NetworkMessage> messageClass = getMessage(id);
        if (messageClass == null) {
            return null;
        }

        try {
            return messageClass.newInstance();
        } catch (NullPointerException | InstantiationException | IllegalAccessException e) {
            throw new Exception("Unable to create new messages for id " + id, e);
        }
    }

    public static MessageManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final MessageManager INSTANCE = new MessageManager();
    }
}
