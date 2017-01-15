package com.omega.dofus.bot.network.types.version;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.io.Serializable;

public class Version implements Serializable {

    protected int major;
    protected int minor;
    protected int release;
    protected int revision;
    protected int patch;
    protected int buildType;

    public Version() {

    }

    public Version(int major, int minor, int release) {
        this.major = major;
        this.minor = minor;
        this.release = release;
    }

    public Version(int major, int minor, int release, int revision, int patch, int buildType) {
        this.major = major;
        this.minor = minor;
        this.release = release;
        this.revision = revision;
        this.patch = patch;
        this.buildType = buildType;
    }

    @Override
    public void serialize(CustomIoBuffer out) {
        if (this.major < 0) {
            throw new Error("Forbidden value (" + this.major + ") on element major.");
        }
        out.writeByte(this.major);
        if (this.minor < 0) {
            throw new Error("Forbidden value (" + this.minor + ") on element minor.");
        }
        out.writeByte(this.minor);
        if (this.release < 0) {
            throw new Error("Forbidden value (" + this.release + ") on element release.");
        }
        out.writeByte(this.release);
        if (this.revision < 0) {
            throw new Error("Forbidden value (" + this.revision + ") on element revision.");
        }
        out.writeInt(this.revision);
        if (this.patch < 0) {
            throw new Error("Forbidden value (" + this.patch + ") on element patch.");
        }
        out.writeByte(this.patch);
        out.writeByte(this.buildType);
    }

    @Override
    public void deserialize(CustomIoBuffer in) throws Exception {
        this.major = in.readByte();
        if (this.major < 0) {
            throw new Error("Forbidden value (" + this.major + ") on element of Version.major.");
        }
        this.minor = in.readByte();
        if (this.minor < 0) {
            throw new Error("Forbidden value (" + this.minor + ") on element of Version.minor.");
        }
        this.release = in.readByte();
        if (this.release < 0) {
            throw new Error(
                    "Forbidden value (" + this.release + ") on element of Version.release.");
        }
        this.revision = in.readInt();
        if (this.revision < 0) {
            throw new Error(
                    "Forbidden value (" + this.revision + ") on element of Version.revision.");
        }
        this.patch = in.readByte();
        if (this.patch < 0) {
            throw new Error("Forbidden value (" + this.patch + ") on element of Version.patch.");
        }
        this.buildType = in.readByte();
        if (this.buildType < 0) {
            throw new Error(
                    "Forbidden value (" + this.buildType + ") on element of Version.buildType.");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Version[major: ").append(major)
                .append(", minor: ").append(minor)
                .append(", release: ").append(release)
                .append(", revision: ").append(revision)
                .append(", patch: ").append(patch)
                .append(", buildType: ").append(buildType)
                .append(']');

        return sb.toString();
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRelease() {
        return release;
    }

    public int getRevision() {
        return revision;
    }

    public int getPatch() {
        return patch;
    }

    public int getBuildType() {
        return buildType;
    }
}
