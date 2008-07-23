package org.openfast.codec.type;

import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;

public class NullableSignedIntegerCodec extends StopBitEncodedTypeCodec implements IntegerCodec {
    public int decode(byte[] buffer, int offset) {
        int value = FastTypeCodecs.SIGNED_INTEGER.decode(buffer, offset);
        if (value > 0)
            return value - 1;
        return value;
    }

    public int encode(byte[] buffer, int offset, int value) {
        // handle maximum value case due to nullable max value being larger than int capacity
        if (value == Integer.MAX_VALUE) {
            buffer[offset] = 8;
            buffer[offset+1] = 0;
            buffer[offset+2] = 0;
            buffer[offset+3] = 0;
            buffer[offset+4] = Fast.STOP_BIT;
            return offset + 5;
        }
        if (value >= 0)
            return FastTypeCodecs.SIGNED_INTEGER.encode(buffer, offset, value + 1);
        return FastTypeCodecs.SIGNED_INTEGER.encode(buffer, offset, value);
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
