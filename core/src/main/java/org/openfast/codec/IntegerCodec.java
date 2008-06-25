package org.openfast.codec;

public interface IntegerCodec extends TypeCodec {
    int decode(byte[] buffer, int offset);
}
