package org.openfast.codec;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.FastObject;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.error.FastConstants;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.Composite;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.util.BitVector;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public class BasicGroupCodec implements GroupCodec {
    private final BitVectorCodec bitVectorCodec;
    private final FieldCodec[] fieldCodecs;
    private final Composite<FastObject> composite;

    public BasicGroupCodec(MessageTemplate template, Composite<FastObject> composite, FastImplementation implementation, DictionaryRegistry dictionaryRegistry) {
        this.bitVectorCodec = implementation.getTypeCodecRegistry().getBitVectorCodec(FastTypes.BIT_VECTOR);
        this.fieldCodecs = new FieldCodec[composite.getFieldCount()];
        this.composite = composite;
        int index = 0;
        CodecFactory codecFactory = implementation.getCodecFactory();
        for (Field field : composite.getFields()) {
            if (field instanceof Scalar) {
                Scalar scalar = (Scalar) field;
                fieldCodecs[index] = codecFactory.createScalarCodec(template, scalar, implementation, dictionaryRegistry);
                index++;
            } else {
                fieldCodecs[index] = codecFactory.createCompositeCodec(template, field, implementation, dictionaryRegistry);
                index++;
            }
        }
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, BitVectorReader pmapReader, Context context) {
        return 0;
    }
    
    public EObject decode(byte[] buffer, int offset, BitVectorReader reader, Context context) {
        BitVector vector = bitVectorCodec.decode(buffer, offset);
        BitVectorReader pmapReader = new BitVectorReader(vector);
        offset += bitVectorCodec.getLength(buffer, offset);
        FastObject o = composite.newObject();
        for (int i=0; i<fieldCodecs.length; i++) {
            offset = fieldCodecs[i].decode(o, i, buffer, offset, pmapReader, context);
        }
        return o;
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, BitVectorBuilder pmapBuilder, Context context) {
        return encode(object.getEObject(index), buffer, offset, context);
    }
    
    public int encode(EObject object, byte[] buffer, int offset, Context context) {
        byte[] temp = context.getTemporaryBuffer();
        int index = 0;
        int pmapLen = 0;
        try {
            BitVectorBuilder pmapBuilder = new BitVectorBuilder(7); // TODO - calculate size of pmap builder
            for (int i=0; i<fieldCodecs.length; i++) {
                index = fieldCodecs[i].encode(object, i, temp, index, pmapBuilder, context);
            }
            pmapLen = bitVectorCodec.encode(buffer, offset, pmapBuilder.getBitVector());
            System.arraycopy(temp, 0, buffer, offset + pmapLen, index);
        } catch (Throwable t) {
            context.getErrorHandler().error(FastConstants.GENERAL_ERROR, "Error occurred while encoding " + object, t);
        } finally {
            context.discardTemporaryBuffer(temp);
        }
        return offset + pmapLen + index;
    }

    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        return 0;
    }
}
