package com.omega.dofus.bot.config.bot;

import com.omega.dofus.bot.Constants;
import com.omega.dofus.bot.config.AbstractConfig;
import com.omega.dofus.bot.config.AbstractConfigReader;
import com.omega.dofus.bot.config.AbstractConfigWriter;

import java.io.File;

public class BotConfig extends AbstractConfig<BotConfig> {

    private final String name;

    private boolean open;
    private String username;
    private String password;
    private short server;

    public BotConfig(String botName) {
        super(new File(
                Constants.CONFIG_BOTS_DIR.getPath() + File.separatorChar + botName + ".xml"));

        this.name = botName;
    }

    @Override
    protected AbstractConfigReader createConfigReader(BotConfig config) {
        return new BotConfigReader(config);
    }

    @Override
    protected AbstractConfigWriter createConfigWriter(BotConfig config) {
        return new BotConfigWriter(config);
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getServer() {
        return server;
    }

    public void setServer(short server) {
        this.server = server;
    }
}
