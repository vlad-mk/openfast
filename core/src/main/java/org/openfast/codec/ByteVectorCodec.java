package org.openfast.codec;


public interface ByteVectorCodec extends TypeCodec {
    byte[] decode(byte[] buffer, int offset);
    int encode(byte[] buffer, int offset, byte[] bytes);
}
