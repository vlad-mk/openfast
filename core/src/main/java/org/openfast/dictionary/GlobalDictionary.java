package org.openfast.dictionary;

import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class GlobalDictionary implements Dictionary {
    FastDictionary dictionary = new BasicFastDictionary();
    
    public DictionaryEntry getEntry(Scalar scalar) {
        if (scalar.getOperator().isPrimitive())
            return dictionary.getEntry(((DictionaryOperator)scalar.getOperator()).getKey(), scalar.getType());
        return dictionary.getEntry(((DictionaryOperator)scalar.getOperator()).getKey(), null);
    }

    public void reset() {
        dictionary.reset();
    }
}
