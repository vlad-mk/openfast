package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.FastDictionary;
import org.openfast.error.FastConstants;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class DeltaAsciiCodec extends DictionaryOperatorStringCodec implements ScalarCodec {
    private IntegerCodec integerCodec;

    public DeltaAsciiCodec(DictionaryOperator operator, IntegerCodec integerCodec, StringCodec stringCodec) {
        super(operator, stringCodec);
        this.integerCodec = integerCodec;
    }

    public int getLength(byte[] buffer, int offset) {
        return 0;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        if (integerCodec.isNull(buffer, offset)) {
            return offset + 1;
        }
        int subtractionLength = integerCodec.decode(buffer, offset);
        offset = integerCodec.getLength(buffer, offset);
        String delta = stringCodec.decode(buffer, offset);
        String value = new StringDelta(subtractionLength, delta).applyTo(getPreviousValue(object, field, context));
        FastDictionary dictionary = context.getDictionary(operator.getDictionary());
        dictionary.store(object.getEntity(), operator.getKey(), context.getCurrentApplicationType(), value);
        object.set(index, value);
        return stringCodec.getLength(buffer, offset) + offset;
    }

    private String getPreviousValue(EObject object, Scalar field, Context context) {
        FastDictionary dictionary = context.getDictionary(operator.getDictionary());
        if (!dictionary.isDefined(object, operator.getKey(), context.getCurrentApplicationType())) {
            if (operator.hasDefaultValue())
                return operator.getDefaultValue();
            return "";
        } else if (dictionary.isNull(object, operator.getKey(), context.getCurrentApplicationType())) {
            context.getErrorHandler().error(FastConstants.D6_MNDTRY_FIELD_NOT_PRESENT, "The field " + field + " must have a priorValue defined.");
            return "";
        }
        return dictionary.lookupString(object.getEntity(), operator.getKey(), context.getCurrentApplicationType());
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
