package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.Scalar;

public class ConstantIntegerCodec implements ScalarCodec {
    private final int defaultValue;

    public ConstantIntegerCodec(int defaultValue) {
        this.defaultValue = defaultValue;
    }
    public int getLength(byte[] buffer, int offset) {
        return 0;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        object.set(index, defaultValue);
        return offset;
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        return offset;
    }
    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {
        object.set(index, defaultValue);
    }
}
