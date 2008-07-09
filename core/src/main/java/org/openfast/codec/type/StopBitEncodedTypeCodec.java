package org.openfast.codec.type;

public class StopBitEncodedTypeCodec {
    public int getLength(byte[] buffer, int offset) {
        if (offset >= buffer.length) return -1;
        int length = 1;
        while ((((int) buffer[offset++]) & 0x80) == 0) {
            length++;
        }
        return length;
    }
}
