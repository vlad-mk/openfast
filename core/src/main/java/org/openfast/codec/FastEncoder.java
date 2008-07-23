package org.openfast.codec;

import java.util.HashMap;
import java.util.Map;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.dictionary.BasicDictionaryRegistry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.TemplateRegistry;

public class FastEncoder implements Coder {
    private final Context context = new Context();
    private final CodecFactory codecFactory;
    private final FastImplementation implementation;
    private final DictionaryRegistry dictionaryRegistry;
    private MessageCodecRegistry codecRegistry = new BasicCodecRegistry();
    private Map<MessageTemplate, MessageHandler> handlers = new HashMap<MessageTemplate, MessageHandler>();

    public FastEncoder(TemplateRegistry templateRegistry) {
        this(FastImplementation.getDefaultVersion(), templateRegistry);
    }
    
    public FastEncoder(FastImplementation implementation, TemplateRegistry templateRegistry) {
        this.implementation = implementation;
        context.setTemplateRegistry(templateRegistry);
        dictionaryRegistry = new BasicDictionaryRegistry(implementation.getDictionaryTypeRegistry());
        codecFactory = implementation.getCodecFactory();
    }

    public int encode(byte[] buffer, int offset, Message message) {
        MessageCodec encoder = getEncoder(message);
        int encoded = encoder.encode(buffer, offset, message, context);
        if (handlers.containsKey(message.getTemplate())) {
            handlers.get(message.getTemplate()).handleMessage(message, context, this);
        }
        return encoded;
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

    public void registerMessageHandler(MessageTemplate template, MessageHandler messageHandler) {
        handlers.put(template, messageHandler);
    }
}
