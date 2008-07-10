package org.openfast.codec.operator;

import org.openfast.codec.IntegerCodec;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.operator.DictionaryOperator;

public abstract class DictionaryOperatorIntegerCodec implements ScalarCodec {
    protected final IntegerCodec integerCodec;
    protected final DictionaryOperator operator;
    protected final int initialValue;
    
    public DictionaryOperatorIntegerCodec(DictionaryOperator operator, IntegerCodec integerDeltaCodec) {
        this.integerCodec = integerDeltaCodec;
        this.operator = operator;
        this.initialValue = operator.hasDefaultValue() ? Integer.parseInt(operator.getDefaultValue()) : 0;
    }
}
