package org.openfast.codec.type;

import org.openfast.codec.BitVectorCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.LongCodec;
import org.openfast.codec.StringCodec;
import org.openfast.codec.TypeCodec;
import org.openfast.codec.ULongCodec;

public class FastTypeCodecs {
    public static final IntegerCodec   UNSIGNED_INTEGER           = new UnsignedIntegerCodec();
    public static final IntegerCodec   SIGNED_INTEGER             = new SignedIntegerCodec();
    public static final IntegerCodec   NULLABLE_UNSIGNED_INTEGER  = new NullableUnsignedIntegerCodec();
    public static final IntegerCodec   NULLABLE_SIGNED_INTEGER    = new NullableSignedIntegerCodec();
    public static final StringCodec    ASCII_STRING               = new AsciiStringCodec();
    public static final StringCodec    NULLABLE_ASCII_STRING      = new NullableAsciiStringCodec();
    public static final BitVectorCodec BIT_VECTOR                 = new BasicBitVectorCodec();
    public static final LongCodec      SIGNED_LONG                = new SignedLongCodec();
    public static final LongCodec      NULLABLE_SIGNED_LONG       = new NullableSignedLongCodec();
    public static final LongCodec      UNSIGNED_LONG              = new UnsignedLongCodec();
    public static final LongCodec      NULLABLE_UNSIGNED_LONG     = new NullableUnsignedLongCodec();
    public static final ULongCodec     ULONG                      = new BasicULongCodec();
    public static final ULongCodec     NULLABLE_ULONG             = new NullableULongCodec();
}
