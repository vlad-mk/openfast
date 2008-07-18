package org.openfast.dictionary;

import org.lasalletech.entity.QName;
import org.openfast.template.Type;

public interface FastDictionary {
    int lookupInt(QName key);
    String lookupString(QName key);
    
    void store(QName key, int value);
    void store(QName key, String value);
    void storeNull(QName key);
    
    boolean isDefined(QName key);
    boolean isNull(QName key);
    
    void reset();
    DictionaryEntry getEntry(QName key);
    DictionaryEntry getEntry(QName key, Type type);
}
