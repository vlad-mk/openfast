package org.openfast.codec;

import org.lasalletech.exom.EObject;
import org.openfast.Context;

public interface FieldCodec<T> {
    int decode(EObject object, int index, byte[] buffer, int offset, T field, Context context);
    int encode(EObject object, int index, byte[] buffer, int offset, T field, Context context);
}
