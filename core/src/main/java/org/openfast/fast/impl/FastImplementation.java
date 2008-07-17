package org.openfast.fast.impl;

import java.util.List;
import org.openfast.codec.CodecFactory;
import org.openfast.codec.TypeCodecRegistry;
import org.openfast.dictionary.DictionaryTypeRegistry;
import org.openfast.template.TypeRegistry;
import org.openfast.template.loader.FieldParser;
import org.openfast.template.loader.OperatorParser;

public abstract class FastImplementation {
    public static final String FAST_1_1 = "FAST 1.1";
    private static FastImplementation fast1x1;
    
    public abstract TypeCodecRegistry getTypeCodecRegistry();
    public abstract CodecFactory getCodecFactory();
    public abstract TypeRegistry getTypeRegistry();
    public abstract DictionaryTypeRegistry getDictionaryTypeRegistry();
    public abstract List<OperatorParser> getOperatorParsers();
    public abstract List<FieldParser> getFieldParsers();
    
    public static FastImplementation getVersion(String version) {
        if (FAST_1_1.equals(version)) {
            return getDefaultVersion();
        }
        throw new IllegalArgumentException("The version " + version + " is not defined.");
    }
    public static FastImplementation getDefaultVersion() {
        synchronized(FAST_1_1) {
            if (fast1x1 == null)
                fast1x1 = new Fast_1_1Implementation();
        }
        return fast1x1;
    }
}
