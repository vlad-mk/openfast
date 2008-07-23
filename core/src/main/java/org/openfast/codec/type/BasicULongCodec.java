package org.openfast.codec.type;

import org.openfast.ULong;
import org.openfast.codec.ULongCodec;

public class BasicULongCodec extends StopBitEncodedTypeCodec implements ULongCodec {
    public ULong decode(byte[] buffer, int offset) {
        long value = FastTypeCodecs.UNSIGNED_LONG.decode(buffer, offset);
        return new ULong(value);
    }

    public int encode(byte[] buffer, int offset, ULong value) {
        return FastTypeCodecs.UNSIGNED_LONG.encode(buffer, offset, value.longValue());
    }

    public boolean isNull(byte[] buffer, int offset) {
        return false;
    }
}
