package org.openfast.dictionary;

import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public interface Dictionary {
    DictionaryEntry getEntry(Scalar scalar);
    void reset();
}
