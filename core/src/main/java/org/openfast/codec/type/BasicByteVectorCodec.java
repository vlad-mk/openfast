package org.openfast.codec.type;

import static org.openfast.codec.type.FastTypeCodecs.UNSIGNED_INTEGER;
import java.nio.ByteBuffer;
import org.openfast.codec.ByteVectorCodec;

public class BasicByteVectorCodec extends LengthEncodedTypeCodec implements ByteVectorCodec {
    public byte[] decode(ByteBuffer buffer) {
        int length = UNSIGNED_INTEGER.decode(buffer);
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return bytes;
    }

    public int encode(byte[] buffer, int offset, byte[] value) {
        offset = UNSIGNED_INTEGER.encode(buffer, offset, value.length);
        System.arraycopy(value, 0, buffer, offset, value.length);
        return offset + value.length;
    }

    public boolean isNull(ByteBuffer buffer) {
        return false;
    }
}
