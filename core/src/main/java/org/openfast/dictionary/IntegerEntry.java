package org.openfast.dictionary;

import org.lasalletech.exom.QName;

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
}
