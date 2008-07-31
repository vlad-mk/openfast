package com.lasalletech.openfast.codec.operator;

import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodecFactory;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;
import com.lasalletech.openfast.template.operator.CacheOperator;

public class CacheCodecFactory implements ScalarCodecFactory {
    public FieldCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        DictionaryEntry entry = dictionaryRegistry.get(((DictionaryOperator) scalar.getOperator()).getDictionary()).getEntry(scalar);
        StringCodec stringCodec = implementation.getTypeCodecRegistry().getStringCodec(scalar.getType());
        IntegerCodec integerCodec = implementation.getTypeCodecRegistry().getIntegerCodec(FastTypes.U16, scalar.isOptional());
        return new CacheStringCodec((CacheOperator) scalar.getOperator(), entry, stringCodec, integerCodec);
    }
}
