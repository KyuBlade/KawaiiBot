package com.omega.dofus.bot;

import java.io.File;

public class Constants {

    public static final File CONFIG_DIR = new File("config");

    public static final File CONFIG_BOTS_DIR = new File(CONFIG_DIR.getPath() + File.separatorChar
                                                                + "bots");
}
