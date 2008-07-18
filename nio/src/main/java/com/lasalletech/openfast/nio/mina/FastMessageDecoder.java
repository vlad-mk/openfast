package com.lasalletech.openfast.nio.mina;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;
import org.openfast.Message;
import org.openfast.codec.FastDecoder;

public class FastMessageDecoder implements MessageDecoder {
    private FastDecoder decoder;
    private int nextMessageLength = 0;

    private FastMessageDecoder(FastDecoder decoder) {
        this.decoder = decoder;
    }
    public MessageDecoderResult decodable(IoSession session, ByteBuffer buffer) {
        byte[] bytes = buffer.array();
        int length = decoder.getNextMessageLength(bytes, 0);
        if (length < 0) {
            return MessageDecoderResult.NEED_DATA;
        }
        nextMessageLength = length;
        return MessageDecoderResult.OK;
    }
    public MessageDecoderResult decode(IoSession session, ByteBuffer buffer, ProtocolDecoderOutput out) throws Exception {
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
