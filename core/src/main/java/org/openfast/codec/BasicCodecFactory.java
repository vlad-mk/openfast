package org.openfast.codec;

import java.util.HashMap;
import java.util.Map;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;

public class BasicCodecFactory implements CodecFactory {
    private Map<String, ScalarCodecFactory> codecFactories = new HashMap<String, ScalarCodecFactory>();

    public MessageCodec createMessageCodec(int id, MessageTemplate template, FastImplementation implementation, DictionaryRegistry dictionaryRegistry) {
        return new BasicMessageCodec(id, template, implementation, dictionaryRegistry, this);
    }

    public ScalarCodec createScalarCodec(MessageTemplate template, Scalar scalar, FastImplementation implementation, DictionaryRegistry dictionaryRegistry) {
        if (!codecFactories.containsKey(scalar.getOperator().getName())) {
            throw new IllegalArgumentException("Encountered unknown operator " + scalar.getOperator() + " in scalar " + scalar.getQName());
        }
        return codecFactories.get(scalar.getOperator().getName()).createCodec(template, scalar, implementation, dictionaryRegistry);
    }

    public void register(String operator, ScalarCodecFactory scalarCodecFactory) {
        codecFactories.put(operator, scalarCodecFactory);
    }
}
