package org.openfast.codec;

public interface LongCodec extends TypeCodec {
    long decode(byte[] buffer, int offset);
    int encode(byte[] buffer, int offset, long value);
}
