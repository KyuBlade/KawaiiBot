package com.omega.dofus.bot.network.handler;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.proxy.AbstractProxyIoHandler;

public class ClientProxyHandler extends AbstractProxyIoHandler {

    private final ClientHandler handler;

    public ClientProxyHandler(ClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public void proxySessionOpened(IoSession session) throws Exception {
        handler.sessionOpened(session);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        handler.sessionCreated(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        handler.sessionIdle(session, status);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        handler.sessionClosed(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        handler.messageReceived(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        handler.messageSent(session, message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        handler.exceptionCaught(session, cause);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        handler.inputClosed(session);
    }
}
