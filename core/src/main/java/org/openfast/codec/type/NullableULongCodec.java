package org.openfast.codec.type;

import org.openfast.Fast;
import org.openfast.ULong;
import org.openfast.codec.ULongCodec;

public class NullableULongCodec extends StopBitEncodedTypeCodec implements ULongCodec {
    public ULong decode(byte[] buffer, int offset) {
        long value = FastTypeCodecs.NULLABLE_UNSIGNED_LONG.decode(buffer, offset);
        return new ULong(value);
    }

    public int encode(byte[] buffer, int offset, ULong value) {
        if (value.longValue() == ULong.MAX_VALUE) {
            buffer[offset]   = 2;
            buffer[offset+1] = 0;
            buffer[offset+2] = 0;
            buffer[offset+3] = 0;
            buffer[offset+4] = 0;
            buffer[offset+5] = 0;
            buffer[offset+6] = 0;
            buffer[offset+7] = 0;
            buffer[offset+8] = 0;
            buffer[offset+9] = Fast.STOP_BIT;
            return offset+10;
        }
        return FastTypeCodecs.NULLABLE_UNSIGNED_LONG.encode(buffer, offset, value.longValue());
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
