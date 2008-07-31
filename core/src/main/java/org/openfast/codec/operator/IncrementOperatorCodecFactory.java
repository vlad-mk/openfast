package org.openfast.codec.operator;

import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.LongCodec;
import org.openfast.codec.ScalarCodecFactory;
import org.openfast.codec.ULongCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class IncrementOperatorCodecFactory implements ScalarCodecFactory {
    public FieldCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        DictionaryOperator operator = (DictionaryOperator) scalar.getOperator();
        DictionaryEntry entry = dictionaryRegistry.get(((DictionaryOperator)scalar.getOperator()).getDictionary()).getEntry(scalar);
        if (FastTypes.U64.equals(scalar.getType())) {
            ULongCodec ulongCodec = implementation.getTypeCodecRegistry().getULongCodec(scalar.getType(), scalar.isOptional());
            return new IncrementULongCodec(entry, operator, ulongCodec);
        } else if (FastTypes.U32.equals(scalar.getType()) || FastTypes.I64.equals(scalar.getType())) {
            LongCodec longCodec = implementation.getTypeCodecRegistry().getLongCodec(scalar.getType(), scalar.isOptional());
            return new IncrementLongCodec(entry, operator, longCodec);
        }
        IntegerCodec integerCodec = implementation.getTypeCodecRegistry().getIntegerCodec(scalar.getType(), scalar.isOptional());
        return new IncrementIntegerCodec(entry, operator, integerCodec);
    }
}
