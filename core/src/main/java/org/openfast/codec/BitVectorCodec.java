package org.openfast.codec;

import org.openfast.util.BitVector;

public interface BitVectorCodec extends TypeCodec {
    BitVector decode(byte[] buffer, int offset);
    int encode(byte[] buffer, int offset, BitVector vector);
}
