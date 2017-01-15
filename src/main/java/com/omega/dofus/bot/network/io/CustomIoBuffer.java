package com.omega.dofus.bot.network.io;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.buffer.IoBufferWrapper;

import java.math.BigInteger;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CustomIoBuffer extends IoBufferWrapper {

    private static final CharsetEncoder charsetEncoder = Charset.forName("UTF-8").newEncoder();
    private static final CharsetDecoder charsetDecoder = Charset.forName("UTF-8").newDecoder();

    private static final int LONG_SIZE = 64;

    private static final int INT_SIZE = 32;

    private static final int SHORT_SIZE = 16;

    private static final int SHORT_MIN_VALUE = -32768;

    private static final int SHORT_MAX_VALUE = 32767;

    private static final int UNSIGNED_SHORT_MAX_VALUE = 65536;

    private static final int CHUNCK_BIT_SIZE = 7;

    private static final int MASK_10000000 = 128;

    private static final int MASK_01111111 = 127;

    public CustomIoBuffer(IoBuffer buf) {
        super(buf);
    }

    public static final CustomIoBuffer allocate(int capacity) {
        return new CustomIoBuffer(IoBuffer.allocate(capacity));
    }

    public static final CustomIoBuffer allocateDirect(int capacity) {
        return new CustomIoBuffer(IoBuffer.allocate(capacity, true));
    }

    public void wrap(IoBuffer buffer) {
        wrap(buffer.buf());
    }

    /**
     * @return all bytes remaining from this buffer
     */
    public byte[] readAllBytes() {
        byte[] bytes = new byte[remaining()];
        get(bytes);

        return bytes;
    }

    public byte[] readBytes(int count) {
        byte[] result = new byte[count];
        get(result);
        return result;
    }

    public byte readByte() {
        return get();
    }

    public short readUnsignedByte() {
        return getUnsigned();
    }

    public boolean readBoolean() {
        return get() > 0;
    }

    public short readShort() {
        return getShort();
    }

    public int readUnsignedShort() {
        return getUnsignedShort();
    }

    public int readInt() {
        return getInt();
    }

    public long readLong() {
        return getLong();
    }

    public double readDouble() {
        return getDouble();
    }

    public String readUTF() throws CharacterCodingException {
        return getPrefixedString(2, charsetDecoder);
    }

    public short readVarShort() throws Exception {
        short finalShort = 0;
        for (int i = 0; i < SHORT_SIZE; i += CHUNCK_BIT_SIZE) {
            byte currentByte = readByte();
            finalShort += (currentByte & MASK_01111111) << i;
            if ((currentByte & MASK_10000000) != MASK_10000000) {
                return finalShort;
            }
        }
        throw new Exception("Too much data");
    }

    public int readVarUhShort() throws Exception {
        return Short.toUnsignedInt(readVarShort());
    }

    public int readVarInt() throws Exception {
        int finalInt = 0;
        for (int i = 0; i < INT_SIZE; i += CHUNCK_BIT_SIZE) {
            byte currentByte = readByte();
            if (i > 0) {
                finalInt += ((currentByte & MASK_01111111) << i);
            } else {
                finalInt += (currentByte & MASK_01111111);
            }

            if ((currentByte & MASK_10000000) != MASK_10000000) {
                return finalInt;
            }
        }
        throw new Exception("Too much data");
    }

    public long readVarUhInt() throws Exception {
        return Integer.toUnsignedLong(readVarInt());
    }

    public long readVarLong() throws Exception {
        long finalLong = 0;
        for (int i = 0; i < LONG_SIZE; i += CHUNCK_BIT_SIZE) {
            byte currentByte = readByte();
            finalLong += (currentByte & MASK_01111111) << i;
            if ((currentByte & MASK_10000000) != MASK_10000000) {
                return finalLong;
            }
        }
        throw new Exception("Too much data");
    }

    public BigInteger readVarUhLong() throws Exception {
        throw new Exception("Not implemented yet.");
    }

    public void writeUTF(CharSequence value) throws CharacterCodingException {
        putPrefixedString(value, 2, charsetEncoder);
    }

    public void writeUTFBytes(CharSequence value) throws CharacterCodingException {
        putString(value, charsetEncoder);
    }

    public void writeByte(byte value) {
        put(value);
    }

    public void writeByte(int value) {
        put((byte) value);
    }

    public void writeUnsignedByte(byte value) {
        putUnsigned(value);
    }

    public void writeBytes(byte[] value) {
        put(value);
    }

    public void writeBytes(IoBuffer value) {
        put(value);
    }

    public void writeShort(short value) {
        putShort(value);
    }

    public void writeUnsignedInt(long value) {
        putUnsignedInt(value);
    }

    public void writeInt(int value) {
        putInt(value);
    }

    public void writeLong(long value) {
        putLong(value);
    }

    public void writeUnsignedLong(BigInteger value) {
        put(value.toByteArray(), 0, 8);
    }

    public void writeDouble(double value) {
        putDouble(value);
    }

    public void writeVarShort(short value) {
        if (value >= 0 && value <= MASK_01111111) {
            writeByte(value);
            return;
        }

        short remaining = (short) (value & 65535);
        int currentByte;
        while (remaining != 0) {
            currentByte = remaining & MASK_01111111;
            remaining >>>= CHUNCK_BIT_SIZE;
            if (remaining > 0) {
                currentByte |= MASK_10000000;
            }
            writeByte(currentByte);
        }
    }

    public void writeVarInt(int value) {
        if (value >= 0 && value <= MASK_01111111) {
            writeByte(value);
            return;
        }

        int remaining = value;
        byte currentByte;
        while (remaining != 0) {
            currentByte = (byte) (remaining & MASK_01111111);
            remaining >>>= CHUNCK_BIT_SIZE;
            if (remaining > 0) {
                currentByte |= MASK_10000000;
            }
            writeByte(currentByte);
        }
    }

    public void writeVarLong(long value) {
        if (value >= 0 && value <= MASK_01111111) {
            writeByte((byte) value);
            return;
        }

        long remaining = value;
        int currentByte;
        while (remaining != 0) {
            writeByte((byte) (remaining & MASK_01111111));
            position(limit() - 1);
            currentByte = readByte();
            remaining >>>= CHUNCK_BIT_SIZE;
            if (remaining > 0) {
                currentByte |= MASK_10000000;
            }
            writeByte(currentByte);
        }
    }

    public void writeSerializable(Serializable value) throws Exception {
        value.serialize(this);
    }

    private void writeInt32(int value) {
        while (value >= MASK_10000000) {
            writeByte(value & MASK_01111111 | MASK_10000000);
            value >>>= CHUNCK_BIT_SIZE;
        }
        writeByte(value);
    }
}