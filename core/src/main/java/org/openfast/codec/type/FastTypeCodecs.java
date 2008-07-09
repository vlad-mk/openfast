package org.openfast.codec.type;

import org.openfast.codec.IntegerCodec;

public class FastTypeCodecs {
    public static final IntegerCodec UNSIGNED_INTEGER = new UnsignedIntegerCodec();
    public static final IntegerCodec SIGNED_INTEGER = new SignedIntegerCodec();
    public static final IntegerCodec NULLABLE_UNSIGNED_INTEGER = new NullableUnsignedIntegerCodec();
}
