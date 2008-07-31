package org.openfast.codec;

import java.nio.ByteBuffer;
import org.openfast.Decimal;

public interface DecimalCodec extends TypeCodec {
    Decimal decode(ByteBuffer buffer);
    int encode(byte[] buffer, int offset, Decimal value);
}
