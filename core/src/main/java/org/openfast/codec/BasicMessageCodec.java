package org.openfast.codec;

import org.lasalletech.exom.Field;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.util.BitVectorBuilder;

public class BasicMessageCodec implements MessageCodec {
    private final int templateId;
    private final IntegerCodec uintCodec;
    @SuppressWarnings("unchecked")
    private final FieldCodec[] fieldCodecs;

    public BasicMessageCodec(int id, MessageTemplate template, FastImplementation implementation, DictionaryRegistry dictionaryRegistry, CodecFactory codecFactory) {
        this.templateId = id;
        this.uintCodec = implementation.getTypeCodecRegistry().getIntegerCodec(FastTypes.U32);
        this.fieldCodecs = new FieldCodec[template.getFieldCount()];
        int index = 0;
        for (Field field : template.getFields()) {
            if (field instanceof Scalar) {
                Scalar scalar = (Scalar) field;
                fieldCodecs[index] = codecFactory.createScalarCodec(template, scalar, implementation, dictionaryRegistry);
                index++;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public int encode(byte[] buffer, int offset, Message message, Context context) {
        byte[] temp = context.getTemporaryBuffer();
        int index = 0;
        BitVectorBuilder pmapBuilder = new BitVectorBuilder(7); // TODO - calculate size of pmap builder
        if (context.getLastTemplateId() != templateId) {
            index = uintCodec.encode(temp, offset, templateId);
            context.setLastTemplateId(templateId);
            pmapBuilder.set();
        }
        int previousIndex;
        for (int i=0; i<fieldCodecs.length; i++) {
            previousIndex = index;
            index = fieldCodecs[i].encode(message, i, temp, index, message.getTemplate().getField(i), context);
            if (index != previousIndex)
                pmapBuilder.set();
            else
                pmapBuilder.skip();
        }
        byte[] pmap = pmapBuilder.getBitVector().getBytes();
        System.arraycopy(pmap, 0, buffer, offset, pmap.length);
        System.arraycopy(temp, 0, buffer, offset + pmap.length, index);
        context.discardTemporaryBuffer(temp);
        return offset + pmap.length + index;
    }
}
