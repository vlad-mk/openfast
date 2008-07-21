package org.openfast.codec.operator;

import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.codec.ScalarCodecFactory;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;
import org.openfast.template.type.IntegerType;

public class DeltaOperatorCodecFactory implements ScalarCodecFactory {
    public ScalarCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        DictionaryEntry entry = dictionaryRegistry.get(((DictionaryOperator) scalar.getOperator()).getDictionary()).getEntry(scalar);
        if (FastTypes.ASCII.equals(scalar.getType())) {
            StringCodec stringCodec = implementation.getTypeCodecRegistry().getStringCodec(scalar.getType());
            IntegerCodec integerCodec = implementation.getTypeCodecRegistry().getIntegerCodec(FastTypes.I32, scalar.isOptional());
            return new DeltaAsciiCodec(entry, (DictionaryOperator) scalar.getOperator(), integerCodec,
                    stringCodec);
        } else if (scalar.getType() instanceof IntegerType) {
            IntegerCodec integerCodec = implementation.getTypeCodecRegistry().getIntegerCodec(scalar.getType(), scalar.isOptional());
            return new DeltaIntegerCodec(entry, (DictionaryOperator) scalar.getOperator(), integerCodec);
        }
        throw new IllegalArgumentException("The type " + scalar.getType() + " found in " + scalar + " is not supported by the delta operator.");
    }
}
