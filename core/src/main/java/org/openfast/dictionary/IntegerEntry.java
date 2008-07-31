package org.openfast.dictionary;

import org.lasalletech.entity.QName;

public class IntegerEntry extends AbstractDictionaryEntry implements DictionaryEntry {
    private int value;

    public IntegerEntry(QName key, int value) {
        super(key);
        this.value = value;
        this.isDefined = true;
    }

    public IntegerEntry(QName key) {
        super(key);
    }

    public int getInt() {
        return value;
    }

    public void set(int value) {
        this.value = value;
        isNull = false;
        isDefined = true;
    }

    public String getString() {
        return String.valueOf(value);
    }

    public void set(String value) {
        set(Integer.parseInt(value));
    }
    @Override
    public String toString() {
        return super.toString();
    }

    public Object get() {
        throw new UnsupportedOperationException();
    }

    public long getLong() {
        return value;
    }

    public void set(long value) {
        this.value = (int) value;
    }

    public void set(Object value) {
        throw new UnsupportedOperationException();
    }

    public Object getObject() {
        throw new UnsupportedOperationException();
    }
}
