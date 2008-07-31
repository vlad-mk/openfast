package org.openfast.dictionary;

import org.lasalletech.entity.QName;

public class ObjectEntry extends AbstractDictionaryEntry {
    private Object value;

    public ObjectEntry(QName key) {
        super(key);
    }

    public int getInt() {
        return 0;
    }

    public long getLong() {
        return 0;
    }

    public Object getObject() {
        return value;
    }

    public String getString() {
        return null;
    }

    public void set(int value) {}

    public void set(long value) {}

    public void set(String value) {}

    public void set(Object value) {
        isDefined = true;
        isNull = false;
        this.value = value;
    }
}
