package org.openfast.dictionary;

import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class GlobalDictionary implements Dictionary {
    FastDictionary dictionary = new BasicFastDictionary();
    
    public DictionaryEntry getEntry(Scalar scalar) {
        return dictionary.getEntry(((DictionaryOperator)scalar.getOperator()).getKey(), scalar.getType());
    }

    public void reset() {
        dictionary.reset();
    }
}
