package org.openfast.dictionary;

import org.lasalletech.exom.QName;

public class NullEntry implements Entry {
    private final QName key;
    private Entry next;

    public NullEntry(QName key) {
        this.key = key;
    }

    public int getInt() {
        throw new UnsupportedOperationException();
    }

    public Entry getNext() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean isNull() {
        return true;
    }

    public boolean matches(Object key) {
        return this.key.equals(key);
    }

    public void setInt(int value) {
        throw new UnsupportedOperationException();
    }

    public void setNext(Entry entry) {
        next = entry;
    }

    public void setNull() {}
}
