package com.omega.dofus.bot;

import com.omega.dofus.bot.config.bot.BotConfig;
import com.omega.dofus.bot.config.global.GlobalConfig;
import com.omega.dofus.bot.logic.connection.managers.AuthentificationManager;
import com.omega.dofus.bot.network.Client;

public class BotInstance {

    private Client client;
    private AuthentificationManager authManager;
    private BotConfig config;

    public BotInstance(BotConfig config) {
        this.config = config;
        this.client = new Client(this,
                                 GlobalConfig.getInstance().getLoginServerHost(),
                                 GlobalConfig.getInstance().getLoginServerPort()
        );
        this.authManager = new AuthentificationManager(config);
    }

    public void connect() throws Exception {
        client.connect();
    }

    public void disconnect() {
        client.disconnect();
    }

    public void switchToGameServer(String host, int port) {
        client.disconnect();
        client = null;

        client = new Client(this, host, port);
        client.connect();
    }

    public Client getClient() {
        return client;
    }

    public AuthentificationManager getAuthentificationManager() {
        return authManager;
    }

    public BotConfig getConfig() {
        return config;
    }
}