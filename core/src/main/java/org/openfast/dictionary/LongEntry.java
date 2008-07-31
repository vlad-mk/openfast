package org.openfast.dictionary;

import org.lasalletech.entity.QName;

public class LongEntry extends AbstractDictionaryEntry implements DictionaryEntry {
    private long value;

    public LongEntry(QName key, long value) {
        super(key);
        this.value = value;
        this.isDefined = true;
    }

    public LongEntry(QName key) {
        super(key);
    }

    public int getInt() {
        return (int) value;
    }

    public void set(int value) {
        set((long) value);
    }

    public String getString() {
        return String.valueOf(value);
    }

    public void set(String value) {
        set(Long.parseLong(value));
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
        this.isDefined = true;
        this.isNull = false;
    }

    public void set(Object value) {
        throw new UnsupportedOperationException();
    }

    public Object getObject() {
        throw new UnsupportedOperationException();
    }
}
