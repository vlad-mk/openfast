package org.openfast.codec.operator;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class CopyIntegerCodec extends DictionaryOperatorIntegerCodec implements FieldCodec {

    public CopyIntegerCodec(DictionaryEntry entry, DictionaryOperator operator, IntegerCodec integerCodec) {
        super(entry, operator, integerCodec);
    }
    
    public int getLength(ByteBuffer buffer) {
        return integerCodec.getLength(buffer);
    }

    public void decode(EObject object, int index, ByteBuffer buffer, Context context) {
        if (integerCodec.isNull(buffer)) {
            dictionaryEntry.setNull();
            return;
        }
        int value = integerCodec.decode(buffer);
        object.set(index, value);
        dictionaryEntry.set(value);
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Context context) {
        boolean dictionaryUndefined = !dictionaryEntry.isDefined();
        boolean dictionaryNull = dictionaryEntry.isNull();
        if (!object.isDefined(index)) {
            dictionaryEntry.setNull();
            if ((dictionaryUndefined && !operator.hasDefaultValue()) || dictionaryNull) {
                return offset;
            } else {
                buffer[offset] = Fast.NULL;
                return offset+1;
            }
        }
        int value = object.getInt(index);
        if (dictionaryUndefined) {
            if ((operator.hasDefaultValue() && initialValue == value)) {
                dictionaryEntry.set(value);
                return offset;
            }
        } else if (!dictionaryNull) {
            if (dictionaryEntry.getInt() == value) {
                dictionaryEntry.set(value);
                return offset;
            }
        }
        int newOffset = integerCodec.encode(buffer, offset, value);
        dictionaryEntry.set(value);
        return newOffset;
    }

    public void decodeEmpty(EObject object, int index, Context context) {
        if (dictionaryEntry.isNull())
            return;
        if (dictionaryEntry.isDefined())
            object.set(index, dictionaryEntry.getInt());
        else if (operator.hasDefaultValue())
            object.set(index, initialValue);
    }
}
