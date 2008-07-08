package org.openfast.codec;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.TemplateRegistry;

public class FastEncoder {
    private final Context context = new Context();
    private CodecFactory codecFactory = FastImplementation.getDefaultVersion().getCodecFactory();
    private MessageCodecRegistry codecRegistry = new BasicCodecRegistry();
    private TypeCodecRegistry typeCodecRegistry = FastImplementation.getDefaultVersion().getTypeCodecRegistry();

    public FastEncoder(TemplateRegistry templateRegistry) {
        context.setTemplateRegistry(templateRegistry);
    }
    
    public void setFastImplementation(FastImplementation implementation) {
        codecFactory = implementation.getCodecFactory();
    }

    public int encode(byte[] buffer, int offset, Message message) {
        MessageCodec encoder = getEncoder(message);
        return encoder.encode(buffer, offset, message, context);
    }

    private MessageCodec getEncoder(Message message) {
        int id = context.getTemplateRegistry().getId(message.getTemplate());
        if (!codecRegistry.isRegistered(id)) {
            codecRegistry.register(id, codecFactory.createCodec(id, message.getTemplate(), typeCodecRegistry));
        }
        return codecRegistry.get(id);
    }
}
