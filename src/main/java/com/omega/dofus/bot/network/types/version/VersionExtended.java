package com.omega.dofus.bot.network.types.version;

import com.omega.dofus.bot.network.io.CustomIoBuffer;

public class VersionExtended extends Version {

    private int install;
    private int technology;

    public VersionExtended() {

    }

    public VersionExtended(int major, int minor, int release, int revision, int patch,
                           int buildType, int install, int technology) {
        super(major, minor, release, revision, patch, buildType);

        this.install = install;
        this.technology = technology;
    }

    @Override
    public void serialize(CustomIoBuffer out) {
        super.serialize(out);

        out.writeByte(install);
        out.writeByte(technology);
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        super.deserialize(in);

        install = in.readByte();
        if (this.install < 0) {
            throw new Error("Forbidden value (" + this.install +
                                    ") on element of VersionExtended.install.");
        }
        technology = in.readByte();
        if (this.technology < 0) {
            throw new Error("Forbidden value (" + this.technology +
                                    ") on element of VersionExtended.technology.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("VersionExtended[major: ").append(major)
                .append(", minor: ").append(minor)
                .append(", release: ").append(release)
                .append(", revision: ").append(revision)
                .append(", patch: ").append(patch)
                .append(", buildType: ").append(buildType)
                .append(", install: ").append(install)
                .append(", technology: ").append(technology)
                .append(']');

        return sb.toString();
    }
}
