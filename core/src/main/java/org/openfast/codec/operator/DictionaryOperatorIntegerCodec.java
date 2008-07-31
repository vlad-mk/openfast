package org.openfast.codec.operator;

import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.SinglePresenceMapEntryFieldCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.template.operator.DictionaryOperator;

public abstract class DictionaryOperatorIntegerCodec extends SinglePresenceMapEntryFieldCodec implements FieldCodec {
    protected final IntegerCodec integerCodec;
    protected final DictionaryOperator operator;
    protected final int initialValue;
    protected final DictionaryEntry dictionaryEntry;
    
    public DictionaryOperatorIntegerCodec(DictionaryEntry entry, DictionaryOperator operator, IntegerCodec integerDeltaCodec) {
        if (entry == null || operator == null || integerDeltaCodec == null) throw new NullPointerException();
        this.dictionaryEntry = entry;
        this.integerCodec = integerDeltaCodec;
        this.operator = operator;
        this.initialValue = operator.hasDefaultValue() ? Integer.parseInt(operator.getDefaultValue()) : 0;
    }
}
