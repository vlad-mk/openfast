package org.openfast.codec;

import org.openfast.codec.operator.IncrementIntegerCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class IncrementOperatorCodecFactory implements ScalarCodecFactory {
    public ScalarCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        IntegerCodec integerCodec = implementation.getTypeCodecRegistry().getIntegerCodec(scalar.getType(), scalar.isOptional());
        DictionaryOperator operator = (DictionaryOperator) scalar.getOperator();
        DictionaryEntry entry = dictionaryRegistry.get(((DictionaryOperator)scalar.getOperator()).getDictionary()).getEntry(scalar);
        return new IncrementIntegerCodec(entry, operator, integerCodec);
    }
}
