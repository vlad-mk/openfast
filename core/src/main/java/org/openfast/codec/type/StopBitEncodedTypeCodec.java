package org.openfast.codec.type;

import org.openfast.codec.TypeCodec;

public abstract class StopBitEncodedTypeCodec implements TypeCodec {
    public int getLength(byte[] buffer, int offset) {
        if (offset >= buffer.length) return -1;
        int length = 1;
        while ((((int) buffer[offset]) & 0x80) == 0) {
            offset++;
            length++;
        }
        return length;
    }
}
