package org.openfast.codec.operator;

import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodecFactory;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.type.IntegerType;
import org.openfast.template.type.StringType;

public class NoneOperatorCodecFactory implements ScalarCodecFactory {
    public FieldCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        if (scalar.getType() instanceof IntegerType) {
            IntegerCodec integerCodec = implementation.getTypeCodecRegistry().getIntegerCodec(scalar.getType(), scalar.isOptional());
            return new NoneIntegerCodec(integerCodec);
        } else if (scalar.getType() instanceof StringType) {
            StringCodec stringCodec = implementation.getTypeCodecRegistry().getStringCodec(scalar.getType(), scalar.isOptional());
            return new NoneStringCodec(stringCodec);
        }
        return null;
    }
}
