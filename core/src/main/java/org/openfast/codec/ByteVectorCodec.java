package org.openfast.codec;

import java.nio.ByteBuffer;


public interface ByteVectorCodec extends TypeCodec {
    byte[] decode(ByteBuffer buffer);
    int encode(byte[] buffer, int offset, byte[] bytes);
}
