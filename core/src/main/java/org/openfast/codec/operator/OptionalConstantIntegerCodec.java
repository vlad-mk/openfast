package org.openfast.codec.operator;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.Scalar;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public class OptionalConstantIntegerCodec implements ScalarCodec {
    private final int defaultValue;

    public OptionalConstantIntegerCodec(int defaultValue) {
        this.defaultValue = defaultValue;
    }
    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, BitVectorReader reader, Context context) {
        if (reader.read())
            object.set(index, defaultValue);
        return offset;
    }
    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, BitVectorBuilder pmapBuilder, Context context) {
        if (object.isDefined(index))
            pmapBuilder.set();
        else
            pmapBuilder.skip();
        return offset;
    }
    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        return 0;
    }
}
