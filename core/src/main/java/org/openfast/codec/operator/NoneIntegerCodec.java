package org.openfast.codec.operator;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.template.Scalar;

public class NoneIntegerCodec extends AlwaysPresentCodec implements FieldCodec {
    private IntegerCodec integerCodec;

    public NoneIntegerCodec(IntegerCodec integerCodec) {
        this.integerCodec = integerCodec;
    }
    public int getLength(ByteBuffer buffer) {
        return integerCodec.getLength(buffer);
    }

    public void decode(EObject object, int index, ByteBuffer buffer, Context context) {
        if (!integerCodec.isNull(buffer)) {
            object.set(index, integerCodec.decode(buffer));
        }
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Context context) {
        if (!object.isDefined(index)) {
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        return integerCodec.encode(buffer, offset, object.getInt(index));
    }
}
