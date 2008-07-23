package org.openfast.codec.operator;

import org.openfast.codec.ScalarCodec;
import org.openfast.codec.SinglePresenceMapEntryFieldCodec;
import org.openfast.codec.ULongCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public abstract class DictionaryOperatorULongCodec extends SinglePresenceMapEntryFieldCodec<Scalar> implements ScalarCodec {
    protected final ULongCodec longCodec;
    protected final DictionaryOperator operator;
    protected final long initialValue;
    protected final DictionaryEntry dictionaryEntry;
    
    public DictionaryOperatorULongCodec(DictionaryEntry entry, DictionaryOperator operator, ULongCodec longCodec) {
        if (entry == null || operator == null || longCodec == null) throw new NullPointerException();
        this.dictionaryEntry = entry;
        this.longCodec = longCodec;
        this.operator = operator;
        this.initialValue = operator.hasDefaultValue() ? Long.parseLong(operator.getDefaultValue()) : 0;
    }
}
