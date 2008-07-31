package org.openfast.codec;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public abstract class SinglePresenceMapEntryFieldCodec implements FieldCodec {
    
    public int encode(EObject object, int index, byte[] buffer, int offset, BitVectorBuilder pmapBuilder, Context context) {
        int newOffset = encode(object, index, buffer, offset, context);
        if (newOffset == offset)
            pmapBuilder.skip();
        else
            pmapBuilder.set();
        return newOffset;
    };
    
    public int decode(EObject object, int index, byte[] buffer, int offset, BitVectorReader reader, Context context) {
        if (reader.read()) {
            decode(object, index, buffer, offset, context);
            return offset + getLength(buffer, offset);
        }
        decodeEmpty(object, index, context);
        return offset;
    }
    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        if (reader.read())
            return getLength(buffer, offset);
        return 0;
    }
    public abstract int encode(EObject object, int index, byte[] buffer, int offset, Context context);
    public abstract void decode(EObject object, int index, byte[] buffer, int offset, Context context);
    public abstract void decodeEmpty(EObject object, int index, Context context);
    public abstract int getLength(byte[] buffer, int offset);
}
