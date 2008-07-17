package org.openfast.codec.operator;

import static org.openfast.Fast.NULL;
import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.error.FastConstants;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class DeltaIntegerCodec extends DictionaryOperatorIntegerCodec implements ScalarCodec {

    public DeltaIntegerCodec(DictionaryEntry entry, DictionaryOperator operator, IntegerCodec integerDeltaCodec) {
        super(entry, operator, integerDeltaCodec);
    }
    
    public int getLength(byte[] buffer, int offset) {
        return 0;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (integerCodec.isNull(buffer, offset)) {
            return offset;
        }
        int delta = integerCodec.decode(buffer, offset);
        int previousValue = getPreviousValue(object, context, field);
        int newValue = delta + previousValue;
        object.set(index, newValue);
        dictionaryEntry.set(newValue);
        return integerCodec.getLength(buffer, offset) + offset;
    }

    private int getPreviousValue(EObject object, Context context, Scalar field) {
        if (!dictionaryEntry.isDefined()) {
            return initialValue;
        } else if (dictionaryEntry.isNull()) {
            context.getErrorHandler().error(FastConstants.D6_MNDTRY_FIELD_NOT_PRESENT, "The field " + field + " must have a priorValue defined.");
            return 0;
        }
        return dictionaryEntry.getInt();
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {}

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (!object.isDefined(index)) {
            buffer[offset] = NULL;
            return offset + 1;
        }
        int value = object.getInt(index);
        int previousValue = getPreviousValue(object, context, field);
        return integerCodec.encode(buffer, offset, value - previousValue);
    }
}
