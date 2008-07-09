package org.openfast.codec;

public interface TypeCodec {
    int getLength(byte[] buffer, int offset);
    boolean isNull(byte[] buffer, int offset);
}
