package org.openfast.codec.type;

import static org.openfast.Fast.STOP_BIT;
import static org.openfast.Fast.VALUE_BITS;
import org.openfast.codec.BitVectorCodec;
import org.openfast.util.BitVector;

public class BasicBitVectorCodec extends StopBitEncodedTypeCodec implements BitVectorCodec {
    public BitVector decode(byte[] buffer, int offset) {
        int len = getLength(buffer, offset);
        byte[] newBuffer = new byte[len];
        System.arraycopy(buffer, offset, newBuffer, 0, len);
        return new BitVector(newBuffer);
    }

    public int encode(byte[] buffer, int offset, BitVector vector) {
        byte[] bytes = vector.getBytes();
        int index = bytes.length - 1;
        while (index > 0 && (bytes[index] & VALUE_BITS) == 0)
            index--;
        System.arraycopy(bytes, 0, buffer, offset, index + 1);
        buffer[index] |= STOP_BIT;
        return index+1;
    }

    public boolean isNull(byte[] buffer, int offset) {
        return false;
    }
}
