package org.openfast.codec;

import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;

public interface ScalarCodecFactory {
    ScalarCodec createCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation, DictionaryRegistry dictionaryRegistry);
}
