package com.omega.dofus.bot.network;

import com.omega.dofus.bot.BotInstance;
import com.omega.dofus.bot.config.global.GlobalConfig;
import com.omega.dofus.bot.network.handler.ClientDirectHandler;
import com.omega.dofus.bot.network.handler.ClientHandler;
import com.omega.dofus.bot.network.handler.ClientProxyHandler;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.proxy.ProxyConnector;
import org.apache.mina.proxy.handlers.socks.SocksProxyConstants;
import org.apache.mina.proxy.handlers.socks.SocksProxyRequest;
import org.apache.mina.proxy.session.ProxyIoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private final Executor executor;

    private IoConnector connector;
    private IoSession session;

    private String host;
    private int port;

    public Client(BotInstance botInstance, String host, int port) {
        this.host = host;
        this.port = port;

        final GlobalConfig config = GlobalConfig.getInstance();
        if (config.isUseProxy()) {
            connector =
                    createProxyConnector(botInstance, config.getProxyHost(), config.getProxyPort(),
                                         config.getProxyVersion(),
                                         host, port);
        } else {
            connector = createDirectConnector(botInstance);
        }
        connector.setConnectTimeoutMillis(config.getConnectionTimeout());
        connector.getSessionConfig().setMaxReadBufferSize(8192);

        executor = Executors.newSingleThreadExecutor();
    }

    private IoConnector createDirectConnector(BotInstance botInstance) {
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("codec",
                                           new ProtocolCodecFilter(
                                                   new MessageCodecFactory()
                                           )
        );
        connector.setHandler(new ClientDirectHandler(new ClientHandler(botInstance)));

        return connector;
    }

    private IoConnector createProxyConnector(BotInstance botInstance, String proxyHost,
                                             int proxyPort, byte protocolVersion,
                                             String targetHost, int targetPort) {
        ProxyConnector connector = new ProxyConnector(new NioSocketConnector());
        connector.setProxyIoSession(
                new ProxyIoSession(
                        new InetSocketAddress(proxyHost, proxyPort),
                        new SocksProxyRequest(
                                protocolVersion,
                                SocksProxyConstants.ESTABLISH_TCPIP_STREAM,
                                new InetSocketAddress(targetHost, targetPort),
                                "unknown"
                        )
                )
        );
        connector.getConnector().getFilterChain().addLast("codec",
                                                          new ProtocolCodecFilter(
                                                                  new MessageCodecFactory()
                                                          )
        );
        connector.setHandler(new ClientProxyHandler(new ClientHandler(botInstance)));

        return connector;
    }

    public void connect() {
        executor.execute(() -> {
            if (connector instanceof ProxyConnector) {
                ProxyConnector connector = (ProxyConnector) this.connector;
                ProxyIoSession session = connector.getProxyIoSession();
                InetSocketAddress proxyAddress = session.getProxyAddress();
                LOGGER.info("Connecting to {}:{}, from proxy {}:{}", host, port,
                            proxyAddress.getHostString(), proxyAddress.getPort());
            } else {
                LOGGER.info("Connecting to {}:{}", host, port);
            }

            final GlobalConfig config = GlobalConfig.getInstance();
            final int maxRetry = config.getConnectionMaxRetry();
            for (int i = 1; i <= maxRetry; i++) {
                try {
                    ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
                    future.awaitUninterruptibly();
                    session = future.getSession();
                    break;
                } catch (RuntimeIoException e) {
                    LOGGER.warn("Failed to connect.\r\nRetry in {} seconds. ({}/{})",
                                config.getConnectionRetryDelay() / 1000L, i,
                                maxRetry);
                    if (i == maxRetry) {
                        LOGGER.warn(
                                "Unable to connect to the server after " + maxRetry + " retries",
                                e.getCause());
                        return;
                    }
                    try {
                        Thread.sleep(config.getConnectionRetryDelay());
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void send(NetworkMessage message) {
        session.write(message);
    }

    public void disconnect() {
        session.closeNow();
    }

    public IoSession getSession() {
        return session;
    }
}
