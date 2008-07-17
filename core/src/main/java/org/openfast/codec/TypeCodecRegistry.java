package org.openfast.codec;

import org.openfast.template.Type;

public interface TypeCodecRegistry {

    IntegerCodec getIntegerCodec(Type type);
    IntegerCodec getIntegerCodec(Type type, boolean nullable);
    StringCodec getStringCodec(Type type);
    StringCodec getStringCodec(Type type, boolean nullable);

    void register(Type type, TypeCodec codec);
    void register(Type type, boolean nullable, TypeCodec codec);

    
}
