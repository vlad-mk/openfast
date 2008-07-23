package org.openfast.codec.type;

import org.openfast.codec.TypeCodec;

public abstract class LengthEncodedTypeCodec implements TypeCodec {
    public int getLength(byte[] buffer, int offset) {
        int lengthLength = FastTypeCodecs.UNSIGNED_INTEGER.getLength(buffer, offset);
        return lengthLength + FastTypeCodecs.UNSIGNED_INTEGER.decode(buffer, offset);
    }
}
