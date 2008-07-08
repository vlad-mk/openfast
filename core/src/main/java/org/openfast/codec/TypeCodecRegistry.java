package org.openfast.codec;

import org.openfast.template.Type;

public interface TypeCodecRegistry {

    IntegerCodec getIntegerCodec(Type intType);
    
}
