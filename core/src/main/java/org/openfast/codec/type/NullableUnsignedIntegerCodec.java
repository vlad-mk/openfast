package org.openfast.codec.type;

import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;

public class NullableUnsignedIntegerCodec extends StopBitEncodedTypeCodec implements IntegerCodec {
    public int decode(byte[] buffer, int offset) {
        return FastTypeCodecs.UNSIGNED_INTEGER.decode(buffer, offset) - 1;
    }

    public int encode(byte[] buffer, int offset, int value) {
        if (value == Integer.MAX_VALUE) {
            buffer[offset] = 8;
            buffer[offset+1] = 0;
            buffer[offset+2] = 0;
            buffer[offset+3] = 0;
            buffer[offset+4] = Fast.STOP_BIT;
            return offset + 5;
        }
        return FastTypeCodecs.UNSIGNED_INTEGER.encode(buffer, offset, value + 1);
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
