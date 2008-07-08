package org.openfast.codec;

import java.util.HashMap;
import java.util.Map;
import org.openfast.template.Type;

public class BasicTypeCodecRegistry implements TypeCodecRegistry {
    private Map<Type, TypeCodec> codecs = new HashMap<Type, TypeCodec>();

    public IntegerCodec getIntegerCodec(Type intType) {
        return (IntegerCodec) codecs.get(intType);
    }

    public void register(Type type, TypeCodec codec) {
        codecs.put(type, codec);
    }
}
