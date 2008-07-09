package org.openfast.codec;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.template.Scalar;

public interface FieldCodec<T> {
    int decode(EObject object, int index, byte[] buffer, int offset, T field, Context context);
    void decodeEmpty(EObject object, int index, Scalar scalar, Context context);
    int encode(EObject object, int index, byte[] buffer, int offset, T field, Context context);
}
