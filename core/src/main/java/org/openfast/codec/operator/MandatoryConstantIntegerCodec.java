package org.openfast.codec.operator;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.codec.FieldCodec;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;

public class MandatoryConstantIntegerCodec implements FieldCodec {
    private final int defaultValue;

    public MandatoryConstantIntegerCodec(int defaultValue) {
        this.defaultValue = defaultValue;
    }
    public int decode(EObject object, int index, byte[] buffer, int offset, BitVectorReader reader, Context context) {
        object.set(index, defaultValue);
        return offset;
    }
    public int encode(EObject object, int index, byte[] buffer, int offset, BitVectorBuilder pmapBuilder, Context context) {
        return offset;
    }
    public int getLength(byte[] buffer, int offset, BitVectorReader reader) {
        return 0;
    }
}
