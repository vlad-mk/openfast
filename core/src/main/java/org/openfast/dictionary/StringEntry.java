package org.openfast.dictionary;

import org.lasalletech.exom.QName;

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
}
