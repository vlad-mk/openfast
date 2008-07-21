package org.openfast.codec.operator;

import org.openfast.codec.ScalarCodec;
import org.openfast.codec.ScalarCodecFactory;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.type.IntegerType;

public class ConstantOperatorCodecFactory implements ScalarCodecFactory {
    public ScalarCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        if (scalar.getType() instanceof IntegerType) {
            int defaultValue = Integer.parseInt(scalar.getOperator().getDefaultValue());
            if (scalar.isOptional()) {
                return new OptionalConstantIntegerCodec(defaultValue);
            }
            return new MandatoryConstantIntegerCodec(defaultValue);
        }
        return null;
    }
}
