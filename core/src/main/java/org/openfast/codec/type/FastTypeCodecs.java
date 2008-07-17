package org.openfast.codec.type;

import org.openfast.codec.IntegerCodec;
import org.openfast.codec.StringCodec;

public class FastTypeCodecs {
    public static final IntegerCodec UNSIGNED_INTEGER           = new UnsignedIntegerCodec();
    public static final IntegerCodec SIGNED_INTEGER             = new SignedIntegerCodec();
    public static final IntegerCodec NULLABLE_UNSIGNED_INTEGER  = new NullableUnsignedIntegerCodec();
    public static final IntegerCodec NULLABLE_SIGNED_INTEGER    = new NullableSignedIntegerCodec();
    public static final StringCodec  ASCII_STRING               = new AsciiStringCodec();
    public static final StringCodec  NULLABLE_ASCII_STRING      = new NullableAsciiStringCodec();
}
