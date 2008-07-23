package org.openfast.codec.type;

import static org.openfast.codec.type.FastTypeCodecs.UNSIGNED_INTEGER;
import org.openfast.codec.ByteVectorCodec;

public class BasicByteVectorCodec extends LengthEncodedTypeCodec implements ByteVectorCodec {
    public byte[] decode(byte[] buffer, int offset) {
        int length = UNSIGNED_INTEGER.decode(buffer, offset);
        offset = UNSIGNED_INTEGER.getLength(buffer, offset) + offset;
        byte[] bytes = new byte[length];
        System.arraycopy(buffer, offset, bytes, 0, length);
        return bytes;
    }

    public int encode(byte[] buffer, int offset, byte[] value) {
        offset = UNSIGNED_INTEGER.encode(buffer, offset, value.length);
        System.arraycopy(value, 0, buffer, offset, value.length);
        return offset + value.length;
    }

    public boolean isNull(byte[] buffer, int offset) {
        return false;
    }
}
