package org.openfast.codec.operator;

import org.openfast.codec.ScalarCodec;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.template.operator.DictionaryOperator;

public abstract  class DictionaryOperatorStringCodec implements ScalarCodec {
    protected final DictionaryOperator operator;
    protected final StringCodec stringCodec;
    protected final DictionaryEntry dictionaryEntry;

    protected DictionaryOperatorStringCodec(DictionaryEntry dictionaryEntry, DictionaryOperator operator, StringCodec stringCodec) {
        if (dictionaryEntry == null || operator == null || stringCodec == null) throw new NullPointerException();
        this.dictionaryEntry = dictionaryEntry;
        this.operator = operator;
        this.stringCodec = stringCodec;
    }
}
