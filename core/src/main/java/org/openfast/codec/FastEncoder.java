package org.openfast.codec;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.dictionary.BasicDictionaryRegistry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.TemplateRegistry;

public class FastEncoder implements Coder {
    private final Context context = new Context();
    private final CodecFactory codecFactory;
    private final FastImplementation implementation;
    private final DictionaryRegistry dictionaryRegistry;
    private MessageCodecRegistry codecRegistry = new BasicCodecRegistry();

    public FastEncoder(TemplateRegistry templateRegistry) {
        this(FastImplementation.getDefaultVersion(), templateRegistry);
    }
    
    public FastEncoder(FastImplementation implementation, TemplateRegistry templateRegistry) {
        this.implementation = implementation;
        context.setTemplateRegistry(templateRegistry);
        dictionaryRegistry = new BasicDictionaryRegistry(implementation.getDictionaryTypeRegistry());
        codecFactory = implementation.getCodecFactory();
    }

    public void setFastImplementation(FastImplementation implementation) {
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
