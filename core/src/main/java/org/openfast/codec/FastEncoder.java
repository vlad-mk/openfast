package org.openfast.codec;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.dictionary.BasicDictionaryRegistry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.TemplateRegistry;

public class FastEncoder implements Coder {
    private final Context context = new Context();
    private CodecFactory codecFactory = FastImplementation.getDefaultVersion().getCodecFactory();
    private MessageCodecRegistry codecRegistry = new BasicCodecRegistry();
    private FastImplementation implementation = FastImplementation.getDefaultVersion();
    private DictionaryRegistry dictionaryRegistry = new BasicDictionaryRegistry(FastImplementation.getDefaultVersion().getDictionaryTypeRegistry());

    public FastEncoder(TemplateRegistry templateRegistry) {
        context.setTemplateRegistry(templateRegistry);
    }
    
    public void setFastImplementation(FastImplementation implementation) {
        this.implementation = implementation;
        dictionaryRegistry = new BasicDictionaryRegistry(implementation.getDictionaryTypeRegistry());
    }

    public int encode(byte[] buffer, int offset, Message message) {
        MessageCodec encoder = getEncoder(message);
        return encoder.encode(buffer, offset, message, context);
    }
    
    public void reset() {
        dictionaryRegistry.reset();
    }

    private MessageCodec getEncoder(Message message) {
        int id = context.getTemplateRegistry().getId(message.getTemplate());
        if (!codecRegistry.isRegistered(id)) {
            codecRegistry.register(id, codecFactory.createMessageCodec(id, message.getTemplate(), implementation, dictionaryRegistry));
        }
        return codecRegistry.get(id);
    }
}
