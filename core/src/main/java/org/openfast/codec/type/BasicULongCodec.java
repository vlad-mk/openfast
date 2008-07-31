package org.openfast.codec.type;

import java.nio.ByteBuffer;
import org.openfast.ULong;
import org.openfast.codec.ULongCodec;

public class BasicULongCodec extends StopBitEncodedTypeCodec implements ULongCodec {
    public ULong decode(ByteBuffer buffer) {
        long value = FastTypeCodecs.UNSIGNED_LONG.decode(buffer);
        return new ULong(value);
    }

    public int encode(byte[] buffer, int offset, ULong value) {
        return FastTypeCodecs.UNSIGNED_LONG.encode(buffer, offset, value.longValue());
    }

    public boolean isNull(ByteBuffer buffer) {
        return false;
    }
}
