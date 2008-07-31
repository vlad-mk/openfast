package org.openfast.codec;

import java.nio.ByteBuffer;

public interface LongCodec extends TypeCodec {
    long decode(ByteBuffer buffer);
    int encode(byte[] buffer, int offset, long value);
}
