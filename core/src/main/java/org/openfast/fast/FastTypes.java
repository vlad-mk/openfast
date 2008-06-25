package org.openfast.fast;

import org.openfast.template.Type;
import org.openfast.template.type.ByteVectorType;
import org.openfast.template.type.DecimalType;
import org.openfast.template.type.SignedIntegerType;
import org.openfast.template.type.StringType;
import org.openfast.template.type.UnsignedIntegerType;

public interface FastTypes {
    public final static Type U8 = new UnsignedIntegerType(8, 256);
    public final static Type U16 = new UnsignedIntegerType(16, 65536);
    public final static Type U32 = new UnsignedIntegerType(32, 4294967295L);
    public final static Type U64 = new UnsignedIntegerType(64, Long.MAX_VALUE);
    public final static Type I8 = new SignedIntegerType(8, Byte.MIN_VALUE, Byte.MAX_VALUE);
    public final static Type I16 = new SignedIntegerType(16, Short.MIN_VALUE, Short.MAX_VALUE);
    public final static Type I32 = new SignedIntegerType(32, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final static Type I64 = new SignedIntegerType(64, Long.MIN_VALUE, Long.MAX_VALUE);
    public final static Type STRING = new StringType("string", null, null);
    public final static Type ASCII = new StringType("ascii", null, null);
    public final static Type UNICODE = new StringType("unicode", null, null);
    public final static Type BYTE_VECTOR = new ByteVectorType();
    public final static Type DECIMAL = new DecimalType();
    public static final Type[] ALL_TYPES = new Type[] { U8, U16, U32, U64, I8, I16, I32, I64, STRING, ASCII, UNICODE, BYTE_VECTOR,
            DECIMAL };
    public static final Type[] INTEGER_TYPES = new Type[] { U8, U16, U32, U64, I8, I16, I32, I64 }; 
}
