package org.openfast.codec;

import java.nio.ByteBuffer;

public interface StringCodec extends TypeCodec {
    String decode(ByteBuffer buffer);
    int encode(byte[] buffer, int offset, String value);
}
