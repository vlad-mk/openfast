package org.openfast.codec.operator;

import org.openfast.codec.ScalarCodec;
import org.openfast.codec.StringCodec;
import org.openfast.template.operator.DictionaryOperator;

public abstract  class DictionaryOperatorStringCodec implements ScalarCodec {
    protected final DictionaryOperator operator;
    protected final StringCodec stringCodec;

    protected DictionaryOperatorStringCodec(DictionaryOperator operator, StringCodec stringCodec) {
        this.operator = operator;
        this.stringCodec = stringCodec;
    }
}
