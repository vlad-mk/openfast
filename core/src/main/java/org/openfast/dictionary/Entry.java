package org.openfast.dictionary;

public interface Entry {
    void setInt(int value);
    int getInt();
    boolean hasNext();
    Entry getNext();
    boolean matches(Object key);
    boolean isNull();
    void setNext(Entry entry);
    void setNull();
}
