package com.omega.dofus.bot;

import com.omega.dofus.bot.config.bot.BotConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BotManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotManager.class);

    private final List<BotInstance> bots = new ArrayList<>();

    private BotManager() {
    }

    public static BotManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected void init() {
        File botConfDir = Constants.CONFIG_BOTS_DIR;
        String[] fileNames = botConfDir.list((dir, name) -> name.endsWith(".xml"));
        if (fileNames == null) {
            return;
        }

        Arrays.stream(fileNames).forEach(fileName -> {
            String botName = fileName.replace(".xml", "");
            BotConfig config = new BotConfig(botName);
            try {
                config.read();
                bots.add(new BotInstance(config));
            } catch (Exception e) {
                LOGGER.error("Unable to load bot {}" + botName, e);
            }
        });
    }

    public List<BotInstance> getBots() {
        return bots;
    }

    public void create(BotConfig config) {
        try {
            config.save();
            bots.add(new BotInstance(config));
        } catch (Exception e) {
            LOGGER.error("Unable to create bot " + config.getName(), e);
        }
    }

    public void open(String botName) {
        try {
            BotInstance botInstance = get(botName);
            BotConfig config = botInstance.getConfig();
            config.setOpen(true);
            config.save();
        } catch (Exception e) {
            LOGGER.error("Unable to open bot " + botName, e);
        }
    }

    public void close(String botName) {
        BotInstance botInstance = get(botName);
        close(botInstance);
    }

    public void close(BotInstance botInstance) {
        botInstance.disconnect();
        BotConfig config = botInstance.getConfig();
        config.setOpen(false);
        try {
            config.save();
        } catch (Exception e) {
            LOGGER.error("Unable to close bot " + botInstance.getConfig().getName(), e);
        }
    }

    private BotInstance get(String botName) {
        return bots.stream()
                .filter(botInstance -> botInstance.getConfig().getName().equalsIgnoreCase(botName))
                .findAny().orElse(null);
    }

    public List<BotInstance> getOpenedBots() {
        return bots.stream().filter(botInstance -> botInstance.getConfig().isOpen()).collect(
                Collectors.toList());
    }

    private static class SingletonHolder {

        private static BotManager INSTANCE = new BotManager();
    }
}
