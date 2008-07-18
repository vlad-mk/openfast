package org.openfast.codec;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public interface FieldCodec<T> {
    int decode(EObject object, int index, byte[] buffer, int offset, T field, BitVectorReader reader, Context context);
    int encode(EObject object, int index, byte[] buffer, int offset, T field, BitVectorBuilder pmapBuilder, Context context);
    int getLength(byte[] buffer, int offset, BitVectorReader reader);
}
