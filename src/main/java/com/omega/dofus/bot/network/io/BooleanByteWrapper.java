package com.omega.dofus.bot.network.io;

public class BooleanByteWrapper {

    public static byte setFlag(byte booleanByte, int position, boolean value) throws Exception {
        switch (position) {
            case 0:
                if (value) {
                    booleanByte |= 1;
                } else {
                    booleanByte &= 255 - 1;
                }
                break;
            case 1:
                if (value) {
                    booleanByte |= 2;
                } else {
                    booleanByte &= 255 - 2;
                }
                break;
            case 2:
                if (value) {
                    booleanByte |= 4;
                } else {
                    booleanByte &= 255 - 4;
                }
                break;
            case 3:
                if (value) {
                    booleanByte |= 8;
                } else {
                    booleanByte &= 255 - 8;
                }
                break;
            case 4:
                if (value) {
                    booleanByte |= 16;
                } else {
                    booleanByte &= 255 - 16;
                }
                break;
            case 5:
                if (value) {
                    booleanByte |= 32;
                } else {
                    booleanByte &= 255 - 32;
                }
                break;
            case 6:
                if (value) {
                    booleanByte |= 64;
                } else {
                    booleanByte &= 255 - 64;
                }
                break;
            case 7:
                if (value) {
                    booleanByte |= 128;
                } else {
                    booleanByte &= 255 - 128;
                }
                break;
            default:
                throw new Exception("Bytebox overflow.");
        }
        return booleanByte;
    }

    public static boolean getFlag(byte booleanByte, int position) throws Exception {
        switch (position) {
            case 0:
                return (booleanByte & 1) != 0;
            case 1:
                return (booleanByte & 2) != 0;
            case 2:
                return (booleanByte & 4) != 0;
            case 3:
                return (booleanByte & 8) != 0;
            case 4:
                return (booleanByte & 16) != 0;
            case 5:
                return (booleanByte & 32) != 0;
            case 6:
                return (booleanByte & 64) != 0;
            case 7:
                return (booleanByte & 128) != 0;
            default:
                throw new Exception("Bytebox overflow.");
        }
    }
}
