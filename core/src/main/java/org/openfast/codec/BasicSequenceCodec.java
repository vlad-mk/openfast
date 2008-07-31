package org.openfast.codec;

import org.lasalletech.entity.EObject;
import org.lasalletech.entity.EObjectList;
import org.lasalletech.entity.EmptyEObject;
import org.lasalletech.entity.EntityType;
import org.openfast.Context;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.Sequence;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public class BasicSequenceCodec implements FieldCodec {
    public class IntEObjectWrapper extends EmptyEObject {
        private final int value;
        
        public IntEObjectWrapper(int value) {
            this.value = value;
        }
        @Override
        public int getInt(int index) {
            return value;
        }
        
        @Override
        public boolean isDefined(int index) {
            return true;
        }
    }

    private final FieldCodec lengthCodec;
    private final Scalar lengthScalar;
    private final BasicGroupCodec groupCodec;
    
    public BasicSequenceCodec(MessageTemplate template, Field field, FastImplementation implementation,
            DictionaryRegistry dictionaryRegistry, BasicCodecFactory basicCodecFactory) {
        lengthScalar = ((Sequence) ((EntityType)field.getType()).getEntity()).getLength();
        lengthCodec = basicCodecFactory.createScalarCodec(template, lengthScalar, implementation, dictionaryRegistry);
        groupCodec = (BasicGroupCodec) basicCodecFactory.createGroupCodec(template, field, implementation, dictionaryRegistry);
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, BitVectorReader pmapReader, Context context) {
        return 0;
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, BitVectorBuilder pmapBuilder, Context context) {
        if (!object.isDefined(index)) {
            return lengthCodec.encode(EObject.EMPTY, 0, buffer, offset, pmapBuilder, context);
        }
        EObjectList list = object.getList(index);
        EObject wrapper = new IntEObjectWrapper(list.size());
        int newOffset = lengthCodec.encode(wrapper, 0, buffer, offset, pmapBuilder, context);
        for (EObject o : list) {
            newOffset = groupCodec.encode(o, buffer, newOffset, context);
        }
        return newOffset;
    }

    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        return 0;
    }
}
