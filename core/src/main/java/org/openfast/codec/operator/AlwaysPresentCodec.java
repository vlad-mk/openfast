package org.openfast.codec.operator;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.codec.FieldCodec;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public abstract class AlwaysPresentCodec<T> implements FieldCodec<T> {

    public int decode(EObject object, int index, byte[] buffer, int offset, T field, BitVectorReader reader, Context context) {
        decode(object, index, buffer, offset, field, context);
        return offset + getLength(buffer, offset);
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, T field, BitVectorBuilder pmapBuilder, Context context) {
        return encode(object, index, buffer, offset, field, context);
    }
    
    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        return getLength(buffer, offset);
    }
    
    public abstract int getLength(byte[] buffer, int offset);

    public abstract void decode(EObject object, int index, byte[] buffer, int offset, T field, Context context);
    public abstract int encode(EObject object, int index, byte[] buffer, int offset, T field, Context context);
}
