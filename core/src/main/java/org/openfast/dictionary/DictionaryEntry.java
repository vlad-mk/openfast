package org.openfast.dictionary;

import org.lasalletech.entity.QName;
import org.openfast.template.Type;

public interface DictionaryEntry {
    void set(int value);
    void set(long value);
    void set(String value);
    
    int getInt();
    long getLong();
    String getString();
    
    boolean hasNext();
    DictionaryEntry getNext();
    boolean matches(Object key);
    boolean isNull();
    boolean isDefined();
    void setNext(DictionaryEntry entry);
    void setNull();
    QName getKey();
    void reset();
}
