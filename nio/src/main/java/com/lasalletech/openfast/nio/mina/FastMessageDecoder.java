package com.lasalletech.openfast.nio.mina;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.openfast.Message;
import org.openfast.codec.FastDecoder;

public class FastMessageDecoder implements MessageDecoder {
    public static final String DECODER = "DECODER";
    private static final String NEXT_MESSAGE_LENGTH = "NEXT_MESSAGE_LENGTH";
    public MessageDecoderResult decodable(IoSession session, ByteBuffer buffer) {
        FastDecoder decoder = (FastDecoder) session.getAttribute(DECODER);
        byte[] bytes = buffer.array();
        int length = decoder.getNextMessageLength(bytes, buffer.position());
        if (length < 0) {
            return MessageDecoderResult.NEED_DATA;
        }
        session.setAttribute(NEXT_MESSAGE_LENGTH, length);
        return MessageDecoderResult.OK;
    }
    public MessageDecoderResult decode(IoSession session, ByteBuffer buffer, ProtocolDecoderOutput out) throws Exception {
        FastDecoder decoder = (FastDecoder) session.getAttribute(DECODER);
        int nextMessageLength = ((Integer)session.getAttribute(NEXT_MESSAGE_LENGTH)).intValue();
        byte[] arr = new byte[nextMessageLength];
        buffer.get(arr, 0, nextMessageLength);
        Message message = decoder.decode(arr, 0);
        out.write(message);
        if (buffer.hasRemaining()) {
            return decodable(session, buffer);
        }
        return MessageDecoderResult.OK;
    }
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {}
}
