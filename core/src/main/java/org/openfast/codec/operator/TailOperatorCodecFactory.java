package org.openfast.codec.operator;

import org.openfast.codec.ScalarCodec;
import org.openfast.codec.ScalarCodecFactory;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class TailOperatorCodecFactory implements ScalarCodecFactory {
    public ScalarCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry) {
        DictionaryEntry entry = dictionaryRegistry.get(((DictionaryOperator) scalar.getOperator()).getDictionary()).getEntry(scalar);
        if ("ascii".equals(scalar.getType().getName())) {
            StringCodec stringCodec = implementation.getTypeCodecRegistry().getStringCodec(scalar.getType(), scalar.isOptional());
            return new TailAsciiCodec(entry, (DictionaryOperator) scalar.getOperator(), stringCodec);
        }
        return null;
    }
}
