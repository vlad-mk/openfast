package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.ScalarCodec;
import org.openfast.codec.StringCodec;
import org.openfast.template.Scalar;

public class NoneStringCodec implements ScalarCodec {
    private StringCodec stringCodec;

    public NoneStringCodec(StringCodec stringCodec) {
        this.stringCodec = stringCodec;
    }
    public int getLength(byte[] buffer, int offset) {
        return 0;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (!stringCodec.isNull(buffer, offset)) {
            object.set(index, stringCodec.decode(buffer, offset));
        }
        return stringCodec.getLength(buffer, offset);
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {}

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (!object.isDefined(index)) {
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        return stringCodec.encode(buffer, offset, object.getString(index));
    }
}
