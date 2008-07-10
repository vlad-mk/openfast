package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.Scalar;

public class NoneIntegerCodec implements ScalarCodec {
    private IntegerCodec integerCodec;

    public NoneIntegerCodec(IntegerCodec integerCodec) {
        this.integerCodec = integerCodec;
    }
    public int getLength(byte[] buffer, int offset) {
        return 0;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (!integerCodec.isNull(buffer, offset)) {
            object.set(index, integerCodec.decode(buffer, offset));
        }
        return integerCodec.getLength(buffer, offset);
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {}

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (!object.isDefined(index)) {
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        return integerCodec.encode(buffer, offset, object.getInt(index));
    }
}
