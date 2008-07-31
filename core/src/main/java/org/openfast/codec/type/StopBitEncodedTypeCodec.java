package org.openfast.codec.type;

import java.nio.ByteBuffer;
import org.openfast.codec.TypeCodec;

public abstract class StopBitEncodedTypeCodec implements TypeCodec {
    public int getLength(ByteBuffer buffer) {
        int index = buffer.position();
        while ((((int) buffer.get(index)) & 0x80) == 0) {
            index++;
        }
        return index + 1 - buffer.position();
    }
}
