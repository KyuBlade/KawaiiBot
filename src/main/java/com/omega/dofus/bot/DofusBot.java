package com.omega.dofus.bot;

import com.omega.dofus.bot.config.global.GlobalConfig;
import com.omega.dofus.bot.network.messages.MessageManager;

public class DofusBot {

    private DofusBot() {

    }

    public void init() throws Exception {
        MessageManager.getInstance().registerMessages();
        GlobalConfig.getInstance().read();
        BotManager.getInstance().init();
    }

    public static DofusBot getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        private static final DofusBot INSTANCE = new DofusBot();
    }
}
