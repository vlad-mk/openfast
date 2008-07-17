package org.openfast.dictionary;

import org.openfast.template.Scalar;

public interface Dictionary {
    DictionaryEntry getEntry(Scalar scalar);
    void reset();
}
