package org.openfast.codec.operator;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.Scalar;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public class MandatoryConstantStringCodec implements ScalarCodec {
    private final String defaultValue;

    public MandatoryConstantStringCodec(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, BitVectorReader reader, Context context) {
        object.set(index, defaultValue);
        return offset;
    }
    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, BitVectorBuilder pmapBuilder, Context context) {
        return offset;
    }
    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        return 0;
    }
}
