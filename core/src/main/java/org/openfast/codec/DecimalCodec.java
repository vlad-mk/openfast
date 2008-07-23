package org.openfast.codec;

import org.openfast.Decimal;

public interface DecimalCodec extends TypeCodec {
    Decimal decode(byte[] buffer, int offset);
    int encode(byte[] buffer, int offset, Decimal value);
}
