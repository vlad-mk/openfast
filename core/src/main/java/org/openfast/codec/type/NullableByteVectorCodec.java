package org.openfast.codec.type;

import static org.openfast.codec.type.FastTypeCodecs.NULLABLE_UNSIGNED_INTEGER;
import org.openfast.Fast;
import org.openfast.codec.ByteVectorCodec;

public class NullableByteVectorCodec extends LengthEncodedTypeCodec implements ByteVectorCodec {
    public byte[] decode(byte[] buffer, int offset) {
        int length = NULLABLE_UNSIGNED_INTEGER.decode(buffer, offset);
        offset = NULLABLE_UNSIGNED_INTEGER.getLength(buffer, offset) + offset;
        byte[] bytes = new byte[length];
        System.arraycopy(buffer, offset, bytes, 0, length);
        return bytes;
    }

    public int encode(byte[] buffer, int offset, byte[] value) {
        offset = NULLABLE_UNSIGNED_INTEGER.encode(buffer, offset, value.length);
        System.arraycopy(value, 0, buffer, offset, value.length);
        return offset + value.length;
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
