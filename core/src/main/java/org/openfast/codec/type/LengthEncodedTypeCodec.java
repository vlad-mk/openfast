package org.openfast.codec.type;

import java.nio.ByteBuffer;
import org.openfast.codec.TypeCodec;

public abstract class LengthEncodedTypeCodec implements TypeCodec {
    public int getLength(ByteBuffer buffer) {
        int lengthLength = FastTypeCodecs.UNSIGNED_INTEGER.getLength(buffer);
        return lengthLength + FastTypeCodecs.UNSIGNED_INTEGER.decode(buffer);
    }
}
