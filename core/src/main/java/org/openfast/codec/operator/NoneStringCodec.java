package org.openfast.codec.operator;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.StringCodec;

public class NoneStringCodec extends AlwaysPresentCodec implements FieldCodec {
    private StringCodec stringCodec;

    public NoneStringCodec(StringCodec stringCodec) {
        this.stringCodec = stringCodec;
    }
    public int getLength(byte[] buffer, int offset) {
        return stringCodec.getLength(buffer, offset);
    }

    public void decode(EObject object, int index, byte[] buffer, int offset, Context context) {
        if (!stringCodec.isNull(buffer, offset)) {
            object.set(index, stringCodec.decode(buffer, offset));
        }
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Context context) {
        if (!object.isDefined(index)) {
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        return stringCodec.encode(buffer, offset, object.getString(index));
    }
}
