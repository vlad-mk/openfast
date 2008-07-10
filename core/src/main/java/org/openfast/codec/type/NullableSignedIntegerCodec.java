package org.openfast.codec.type;

import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;

public class NullableSignedIntegerCodec extends StopBitEncodedTypeCodec implements IntegerCodec {
    public int decode(byte[] buffer, int offset) {
        return FastTypeCodecs.SIGNED_INTEGER.decode(buffer, offset) - 1;
    }

    public int encode(byte[] buffer, int offset, int value) {
        return FastTypeCodecs.SIGNED_INTEGER.encode(buffer, offset, value + 1);
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
