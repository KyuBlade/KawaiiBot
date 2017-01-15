package com.omega.dofus.bot.network;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.MessageManager;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DofusMessageDecoder extends CumulativeProtocolDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DofusMessageDecoder.class);

    public DofusMessageDecoder() {

    }

    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        while (in.hasRemaining()) {
            // Mark the start of the new packet;
            in.mark();

            if (in.remaining() < 2) {
                LOGGER.info("Not enough data to read the header, byte available : " +
                                    in.remaining() + " (needed : 2)");
                return false;
            }

            int header = in.getUnsignedShort();
            int id = header >> NetworkMessage.BIT_RIGHT_SHIFT_LEN_PACKET_ID;
            int lengthOfLength = header & NetworkMessage.BIT_MASK;
            if (in.remaining() < lengthOfLength) {
                LOGGER.info("Not enough data to read the length, byte available : " +
                                    in.remaining() + " (needed : " + lengthOfLength + ")");
                in.reset();
                return false;
            }

            int length = readMessageLength(lengthOfLength, in);
            if (in.remaining() < length) {
                LOGGER.info("Not enough data to read the messages body, byte available : " +
                                    in.remaining() + " (needed : " + length + ")");
                in.reset();
                return false;
            }

            try {
                NetworkMessage message = MessageManager.getInstance().newMessage(id);
                if (message == null) {
                    LOGGER.warn("No messages with id " + id +
                                        " have been registered. Skipping the data of this message.");
                    in.skip(length);
                    return true;
                }


                message.read(new CustomIoBuffer(in));

                LOGGER.info("[RECV] (ID : {} - Len : {}) {}", message.getId(), length,
                            message.toString());
                out.write(message);
            } catch (Exception e) {
                LOGGER.error("Unable to read message", e);
            }
        }

        return true;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    private int readMessageLength(int lengthOfLength, IoBuffer in) {
        int length = 0;
        switch (lengthOfLength) {
            case 0: {
                break;
            }
            case 1: {
                length = in.getUnsigned();
                break;
            }
            case 2: {
                length = in.getUnsignedShort();
                break;
            }
            case 3: {
                length = ((in.get() & 255) << 16) + ((in.get() & 255) << 8) + (in.get() & 255);
                break;
            }
            default: {
                break;
            }
        }

        return length;
    }
}
