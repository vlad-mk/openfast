package org.openfast.codec.operator;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.error.FastConstants;
import org.openfast.template.operator.DictionaryOperator;

public class DeltaAsciiCodec extends DictionaryOperatorStringCodec implements FieldCodec {
    private IntegerCodec integerCodec;

    public DeltaAsciiCodec(DictionaryEntry dictionaryEntry, DictionaryOperator operator, IntegerCodec integerCodec, StringCodec stringCodec) {
        super(dictionaryEntry, operator, stringCodec);
        if (integerCodec == null) throw new NullPointerException();
        this.integerCodec = integerCodec;
    }

    public int getLength(ByteBuffer buffer) {
        if (integerCodec.isNull(buffer)) return 1;
        int len = integerCodec.getLength(buffer);
        return len + stringCodec.getLength(buffer);
    }

    public void decode(EObject object, int index, ByteBuffer buffer, Context context) {
        if (integerCodec.isNull(buffer)) {
            return;
        }
        int subtractionLength = integerCodec.decode(buffer);
        String delta = stringCodec.decode(buffer);
        String value = new StringDelta(subtractionLength, delta).applyTo(getPreviousValue(object, context));
        dictionaryEntry.set(value);
        object.set(index, value);
    }

    private String getPreviousValue(EObject object, Context context) {
        if (!dictionaryEntry.isDefined()) {
            if (operator.hasDefaultValue())
                return operator.getDefaultValue();
            return "";
        } else if (dictionaryEntry.isNull()) {
            context.getErrorHandler().error(FastConstants.D6_MNDTRY_FIELD_NOT_PRESENT, "The field must have a priorValue defined.");
            return "";
        }
        return dictionaryEntry.getString();
    }

    public void decodeEmpty(EObject object, int index, Context context) {}

    public int encode(EObject object, int index, byte[] buffer, int offset, Context context) {
        if (!object.isDefined(index)){
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        String value = object.getString(index);
        StringDelta diff = StringDelta.diff(value, getPreviousValue(object, context));
        int subtractionLength = diff.getSubtractionLength();
        int newOffset = integerCodec.encode(buffer, offset, subtractionLength);
        dictionaryEntry.set(value);
        return stringCodec.encode(buffer, newOffset, diff.getValue());
    }
}
