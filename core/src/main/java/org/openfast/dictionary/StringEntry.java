package org.openfast.dictionary;

import org.lasalletech.entity.QName;

public class StringEntry extends AbstractDictionaryEntry implements DictionaryEntry {
    private String value;

    public StringEntry(QName key, String value) {
        super(key);
        this.value = value;
        this.isDefined = true;
    }
    
    public StringEntry(QName key) {
        super(key);
    }
    
    public String getString() {
        return value;
    }
    
    public void set(String value) {
        this.value = value;
        isNull = false;
        isDefined = true;
    }
    
    public void set(int value) {
        set(String.valueOf(value));
    }
    
    public int getInt() {
        return Integer.parseInt(value);
    }
    
    @Override
    public void reset() {
        super.reset();
        value = null;
    }

    public Object get() {
        throw new UnsupportedOperationException();
    }

    public long getLong() {
        return Long.parseLong(value);
    }

    public void set(long value) {
        this.value = String.valueOf(value);
    }

    public void set(Object value) {
        this.value = value.toString();
    }
}
