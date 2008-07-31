package org.openfast.codec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.dictionary.BasicDictionaryRegistry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.TemplateRegistry;
import org.openfast.util.BitVectorReader;

public class FastDecoder implements Coder {
    private final Context context = new Context();
    private final CodecFactory codecFactory = FastImplementation.getDefaultVersion().getCodecFactory();
    private final MessageCodecRegistry codecRegistry = new BasicCodecRegistry();
    private final FastImplementation implementation;
    private final DictionaryRegistry dictionaryRegistry;
    private final BitVectorCodec bitVectorCodec;
    private final LongCodec uintCodec;
    private Map<MessageTemplate, MessageHandler> messageHandlers = new HashMap<MessageTemplate, MessageHandler>();

    public FastDecoder(TemplateRegistry templateRegistry) {
        this(FastImplementation.getDefaultVersion(), templateRegistry);
    }

    public FastDecoder(FastImplementation implementation, TemplateRegistry templateRegistry) {
        context.setTemplateRegistry(templateRegistry);
        this.implementation = implementation;
        dictionaryRegistry = new BasicDictionaryRegistry(implementation.getDictionaryTypeRegistry());
        bitVectorCodec = implementation.getTypeCodecRegistry().getBitVectorCodec(FastTypes.BIT_VECTOR);
        uintCodec = implementation.getTypeCodecRegistry().getLongCodec(FastTypes.U32);
    }

    public Message decode(ByteBuffer buffer) {
        BitVectorReader reader = new BitVectorReader(bitVectorCodec.decode(buffer));
        int templateId = 0;
        if (reader.read()) {
            templateId = (int) uintCodec.decode(buffer);
            context.setLastTemplateId(templateId);
        } else {
            templateId = context.getLastTemplateId();
        }
        MessageTemplate template = context.getTemplate(templateId);
        MessageCodec codec = getCodec(templateId, template);
        Message message = template.newObject();
        codec.decode(message, buffer, reader, context);
        if (messageHandlers.containsKey(message.getTemplate()))
            messageHandlers.get(message.getTemplate()).handleMessage(message, context, this);
        return message;
    }
    
    public void reset() {
        dictionaryRegistry.reset();
    }

    private MessageCodec getCodec(int id, MessageTemplate template) {
        if (!codecRegistry.isRegistered(id)) {
            codecRegistry.register(id, codecFactory.createMessageCodec(id, template, implementation, dictionaryRegistry));
        }
        return codecRegistry.get(id);
    }

    public int getNextMessageLength(ByteBuffer buffer) {
        BitVectorReader reader = new BitVectorReader(bitVectorCodec.decode(buffer));
        int length = bitVectorCodec.getLength(buffer);
        int templateId = 0;
        if (reader.read()) {
            templateId = (int) uintCodec.decode(buffer);
            length += uintCodec.getLength(buffer);
        } else {
            templateId = context.getLastTemplateId();
        }
        MessageTemplate template = context.getTemplate(templateId);
        MessageCodec codec = getCodec(templateId, template);
        length += codec.getLength(buffer, reader, context);
        return length;
    }

    public void registerMessageHandler(MessageTemplate template, MessageHandler messageHandler) {
        messageHandlers.put(template, messageHandler);
    }
}
