package com.omega.dofus.bot.network;

import com.omega.dofus.bot.network.io.CustomIoBuffer;
import com.omega.dofus.bot.network.messages.NetworkMessage;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.omega.dofus.bot.network.messages.NetworkMessage.BIT_RIGHT_SHIFT_LEN_PACKET_ID;

public class DofusMessageEncoder extends ProtocolEncoderAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DofusMessageEncoder.class);

    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
            throws Exception {
        if (message instanceof NetworkMessage) {
            NetworkMessage networkMessage = (NetworkMessage) message;

            CustomIoBuffer messageDataBuffer = CustomIoBuffer.allocate(32);
            messageDataBuffer.setAutoExpand(true);
            networkMessage.serialize(messageDataBuffer);
            messageDataBuffer.flip();

            int messageDataLength = messageDataBuffer.remaining();
            int lengthOfLength = computeTypeLen(messageDataLength);

            CustomIoBuffer messageBuffer =
                    CustomIoBuffer.allocate(3 + lengthOfLength + messageDataLength);
            messageBuffer.putShort(subComputeStaticHeader(networkMessage.getId(), lengthOfLength));

            switch (lengthOfLength) {
                case 0: {
                    return;
                }
                case 1: {
                    messageBuffer.writeByte(messageDataLength);
                    break;
                }
                case 2: {
                    messageBuffer.writeShort((short) messageDataLength);
                    break;
                }
                case 3: {
                    messageBuffer.writeByte(messageDataLength >> 16 & 255);
                    messageBuffer.writeShort((short) (messageDataLength & 65535));
                    break;
                }
                default: {
                    break;
                }
            }

            messageBuffer.put(messageDataBuffer);
            messageBuffer.flip();
            LOGGER.info("[SEND] (ID : {} - Len : {}) {}", networkMessage.getId(), messageDataLength,
                        message.toString());

            out.write(messageBuffer);
        } else {
            throw new Exception("Can't send messages " + message);
        }
    }

    private static byte computeTypeLen(long length) {
        if (length > 65535) {
            return 3;
        }
        if (length > 255) {
            return 2;
        }
        if (length > 0) {
            return 1;
        }
        return 0;
    }

    private static short subComputeStaticHeader(int id, int lengthOfLength) {
        return (short) (id << BIT_RIGHT_SHIFT_LEN_PACKET_ID | lengthOfLength);
    }
}
