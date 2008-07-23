package org.openfast.codec.type;

import org.openfast.Fast;
import org.openfast.codec.LongCodec;

public class NullableUnsignedLongCodec extends StopBitEncodedTypeCodec implements LongCodec {
    public long decode(byte[] buffer, int offset) {
        return FastTypeCodecs.UNSIGNED_LONG.decode(buffer, offset) - 1;
    }

    public int encode(byte[] buffer, int offset, long value) {
        if (value == Long.MAX_VALUE) {
            buffer[offset] = 1;
            buffer[offset+1] = 0;
            buffer[offset+2] = 0;
            buffer[offset+3] = 0;
            buffer[offset+4] = 0;
            buffer[offset+5] = 0;
            buffer[offset+6] = 0;
            buffer[offset+7] = 0;
            buffer[offset+8] = 0;
            buffer[offset+9] = Fast.STOP_BIT;
            return offset + 10;
        }
        return FastTypeCodecs.UNSIGNED_LONG.encode(buffer, offset, value + 1);
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
