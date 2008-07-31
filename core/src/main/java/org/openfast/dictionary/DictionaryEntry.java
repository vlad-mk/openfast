package org.openfast.dictionary;

import org.lasalletech.entity.QName;

public interface DictionaryEntry {
    void set(int value);
    void set(long value);
    void set(String value);
    void set(Object value);
    
    int getInt();
    long getLong();
    String getString();
    Object getObject();
    
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
