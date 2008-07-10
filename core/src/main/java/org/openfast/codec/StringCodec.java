package org.openfast.codec;

public interface StringCodec extends TypeCodec {
    String decode(byte[] buffer, int offset);
    int encode(byte[] buffer, int offset, String value);
}
