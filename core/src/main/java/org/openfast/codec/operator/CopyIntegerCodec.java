package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.dictionary.FastDictionary;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class CopyIntegerCodec implements ScalarCodec {
    private final IntegerCodec integerCodec;
    private final int defaultValue;
    private final DictionaryOperator operator;

    public CopyIntegerCodec(DictionaryOperator operator, IntegerCodec integerCodec) {
        this.operator = operator;
        this.integerCodec = integerCodec;
        this.defaultValue = (operator.hasDefaultValue()) ? Integer.parseInt(operator.getDefaultValue()) : 0;
    }
    
    public int getLength(byte[] buffer, int offset) {
        throw new UnsupportedOperationException();
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        FastDictionary dictionary = context.getDictionary(operator.getDictionary());
        if (integerCodec.isNull(buffer, offset)) {
            dictionary.storeNull(object.getEntity(), operator.getKey(), context.getCurrentApplicationType());
            return offset + 1;
        }
        int value = integerCodec.decode(buffer, offset);
        object.set(index, value);
        dictionary.store(object.getEntity(), operator.getKey(), context.getCurrentApplicationType(), value);
        return integerCodec.getLength(buffer, offset) + offset;
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar field, Context context) {
        FastDictionary dictionary = context.getDictionary(operator.getDictionary());
        boolean dictionaryUndefined = !dictionary.isDefined(object, operator.getKey(), context.getCurrentApplicationType());
        boolean dictionaryNull = dictionary.isNull(object, operator.getKey(), context.getCurrentApplicationType());
        if (!object.isDefined(index)) {
            dictionary.storeNull(object.getEntity(), operator.getKey(), context.getCurrentApplicationType());
            if ((dictionaryUndefined && !operator.hasDefaultValue()) || dictionaryNull) {
                return offset;
            } else {
                buffer[offset] = Fast.NULL;
                return offset+1;
            }
        }
        int value = object.getInt(index);
        if (dictionaryUndefined) {
            if ((operator.hasDefaultValue() && defaultValue == value)) {
                dictionary.store(object.getEntity(), operator.getKey(), context.getCurrentApplicationType(), value);
                return offset;
            }
        } else if (!dictionaryNull) {
            if (dictionary.lookupInt(object.getEntity(), operator.getKey(), context.getCurrentApplicationType()) == value) {
                dictionary.store(object.getEntity(), operator.getKey(), context.getCurrentApplicationType(), value);
                return offset;
            }
        }
        int newOffset = integerCodec.encode(buffer, offset, value);
        dictionary.store(object.getEntity(), operator.getKey(), context.getCurrentApplicationType(), value);
        return newOffset;
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, Context context) {
        FastDictionary dictionary = context.getDictionary(operator.getDictionary());
        if (dictionary.isNull(object, operator.getKey(), context.getCurrentApplicationType()))
            return;
        if (dictionary.isDefined(object, operator.getKey(), context.getCurrentApplicationType()))
            object.set(index, dictionary.lookupInt(object.getEntity(), operator.getKey(), null));
        else if (operator.hasDefaultValue())
            object.set(index, defaultValue);
    }
}
