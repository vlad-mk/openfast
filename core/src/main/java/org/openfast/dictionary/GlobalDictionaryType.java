package org.openfast.dictionary;


public class GlobalDictionaryType implements DictionaryType {
    public Dictionary createDictionary() {
        return new GlobalDictionary();
    }
}
