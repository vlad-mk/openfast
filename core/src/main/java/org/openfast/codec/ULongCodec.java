package org.openfast.codec;

import org.openfast.ULong;

public interface ULongCodec extends TypeCodec {
    ULong decode(byte[] buffer, int offset);
    int encode(byte[] buffer, int offset, ULong value);
}
