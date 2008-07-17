package org.openfast.dictionary;

import org.lasalletech.exom.QName;

public interface DictionaryEntry {
    void set(int value);
    void set(String value);
    int getInt();
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
