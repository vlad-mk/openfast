package org.openfast.codec;

import java.util.HashMap;
import java.util.Map;
import org.openfast.template.Type;
import org.openfast.util.Key;

public class BasicTypeCodecRegistry implements TypeCodecRegistry {
    private Map<Key, TypeCodec> codecs = new HashMap<Key, TypeCodec>();

    public IntegerCodec getIntegerCodec(Type type) {
        return (IntegerCodec) getCodec(type, false);
    }

    public StringCodec getStringCodec(Type type) {
        return (StringCodec) getCodec(type, false);
    }

    private TypeCodec getCodec(Type type, boolean nullable) {
        return codecs.get(new Key(type, new Boolean(nullable)));
    }

    public void register(Type type, boolean nullable, TypeCodec codec) {
        codecs.put(new Key(type, new Boolean(nullable)), codec);
    }

    public void register(Type type, TypeCodec codec) {
        register(type, false, codec);
    }

    public IntegerCodec getIntegerCodec(Type type, boolean nullable) {
        return (IntegerCodec) getCodec(type, nullable);
    }

    public StringCodec getStringCodec(Type type, boolean nullable) {
        return (StringCodec) getCodec(type, nullable);
    }

    public BitVectorCodec getBitVectorCodec(Type type) {
        return (BitVectorCodec) getCodec(type, false);
    }

    public LongCodec getLongCodec(Type type) {
        return (LongCodec) getCodec(type, false);
    }

    public LongCodec getLongCodec(Type type, boolean nullable) {
        return (LongCodec) getCodec(type, nullable);
    }

    public ULongCodec getULongCodec(Type type) {
        return null;
    }

    public ULongCodec getULongCodec(Type type, boolean nullable) {
        return null;
    }
}
