package com.omega.dofus.bot.network.handler;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private BotInstance botInstance;

    public ClientHandler(BotInstance botInstance) {
        this.botInstance = botInstance;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        LOGGER.info("Connected");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LOGGER.info("Disconnected");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message instanceof NetworkMessage) {
            NetworkMessage netMessage = ((NetworkMessage) message);
            netMessage.process(this.botInstance);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOGGER.error("Error in client", cause);
    }
}
