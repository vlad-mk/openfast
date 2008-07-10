package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.Operator;
import org.openfast.template.Scalar;

public class DefaultIntegerCodec implements ScalarCodec {
    private final Operator operator;
    private final IntegerCodec integerCodec;
    private final int defaultValue;

    public DefaultIntegerCodec(Operator operator, IntegerCodec integerCodec) {
        this.operator = operator;
        this.integerCodec = integerCodec;
        this.defaultValue = operator.hasDefaultValue() ? Integer.parseInt(operator.getDefaultValue()) : 0;
    }
    public int getLength(byte[] buffer, int offset) {
        return 0;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (integerCodec.isNull(buffer, offset))
            return offset + 1;
        int newOffset = integerCodec.decode(buffer, offset);
        object.set(index, newOffset);
        return newOffset;
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {
        if (operator.hasDefaultValue())
            object.set(index, defaultValue);
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (object.isDefined(index)) {
            if (operator.hasDefaultValue() && object.getInt(index) == defaultValue)
                return offset;
            return integerCodec.encode(buffer, offset, object.getInt(index));
        } else {
            if (!operator.hasDefaultValue())
                return offset;
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
    }
    
}
