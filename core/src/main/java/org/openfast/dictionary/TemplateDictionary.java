package org.openfast.dictionary;

import java.util.HashMap;
import java.util.Map;
import org.lasalletech.exom.QName;
import org.openfast.template.Scalar;
import org.openfast.template.operator.DictionaryOperator;

public class TemplateDictionary implements Dictionary {
    Map<QName, FastDictionary> dictionaries = new HashMap<QName, FastDictionary>();
    public DictionaryEntry getEntry(Scalar scalar) {
        return null;
    }
    public void reset() {}
}
