package org.openfast.codec;

import java.nio.ByteBuffer;

public interface TypeCodec {
    int getLength(ByteBuffer buffer);
    boolean isNull(ByteBuffer buffer);
}
