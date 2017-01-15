package com.omega.dofus.bot.network.io;

public interface Serializable {

    void serialize(CustomIoBuffer out) throws Exception;

    void deserialize(CustomIoBuffer in) throws Exception;
}