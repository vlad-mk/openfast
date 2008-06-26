package org.openfast.dictionary;

import org.lasalletech.exom.QName;

public class IntegerEntry implements Entry {
    private final QName key;
    private int value;
    private Entry next;
    private boolean isNull;

    public IntegerEntry(QName key, int value) {
        if (key == null) throw new NullPointerException();
        this.key = key;
        this.value = value;
    }

    public int getInt() {
        return value;
    }

    public Entry getNext() {
        return next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean isNull() {
        return isNull;
    }

    public boolean matches(Object key) {
        return this.key.equals(key);
    }

    public void setInt(int value) {
        this.value = value;
        isNull = false;
    }

    public void setNext(Entry entry) {
        next = entry;
    }

    public void setNull() {
        isNull = true;
    }
}
