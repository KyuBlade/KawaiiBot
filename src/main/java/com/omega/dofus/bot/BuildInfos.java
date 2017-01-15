package com.omega.dofus.bot;

import com.omega.dofus.bot.network.types.version.Version;
import com.omega.dofus.bot.network.types.version.VersionExtended;

public class BuildInfos {

    public static final Version BUILD_VERSION = new Version(2, 37, 0);

    public static final int BUILD_TYPE = 0;

    public static final int BUILD_REVISION = 111833;

    public static final int BUILD_PATCH = 0;

    public static final VersionExtended VERSION_EXTENDED =
            new VersionExtended(BUILD_VERSION.getMajor(), BUILD_VERSION.getMinor(),
                                BUILD_VERSION.getRelease(), BUILD_REVISION,
                                BUILD_PATCH, BUILD_TYPE, 1, 1);
}
