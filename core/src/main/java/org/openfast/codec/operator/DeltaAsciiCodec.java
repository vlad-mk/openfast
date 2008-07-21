package org.openfast.codec.operator;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.error.FastConstants;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class DeltaAsciiCodec extends DictionaryOperatorStringCodec implements ScalarCodec {
    private IntegerCodec integerCodec;

    public DeltaAsciiCodec(DictionaryEntry dictionaryEntry, DictionaryOperator operator, IntegerCodec integerCodec, StringCodec stringCodec) {
        super(dictionaryEntry, operator, stringCodec);
        if (integerCodec == null) throw new NullPointerException();
        this.integerCodec = integerCodec;
    }

    public int getLength(byte[] buffer, int offset) {
        if (integerCodec.isNull(buffer, offset)) return 1;
        int len = integerCodec.getLength(buffer, offset);
        return len + stringCodec.getLength(buffer, offset + len);
    }

    public void decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (integerCodec.isNull(buffer, offset)) {
            return;
        }
        int subtractionLength = integerCodec.decode(buffer, offset);
        offset = integerCodec.getLength(buffer, offset);
        String delta = stringCodec.decode(buffer, offset);
        String value = new StringDelta(subtractionLength, delta).applyTo(getPreviousValue(object, field, context));
        dictionaryEntry.set(value);
        object.set(index, value);
    }

    private String getPreviousValue(EObject object, Scalar field, Context context) {
        if (!dictionaryEntry.isDefined()) {
            if (operator.hasDefaultValue())
                return operator.getDefaultValue();
            return "";
        } else if (dictionaryEntry.isNull()) {
            context.getErrorHandler().error(FastConstants.D6_MNDTRY_FIELD_NOT_PRESENT, "The field " + field + " must have a priorValue defined.");
            return "";
        }
        return dictionaryEntry.getString();
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {}

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (!object.isDefined(index)){
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        StringDelta diff = StringDelta.diff(object.getString(index), getPreviousValue(object, field, context));
        int newOffset = integerCodec.encode(buffer, offset, diff.getSubtractionLength());
        return stringCodec.encode(buffer, newOffset, diff.getValue());
    }
}
