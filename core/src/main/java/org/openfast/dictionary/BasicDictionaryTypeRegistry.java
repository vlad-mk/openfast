package org.openfast.dictionary;

import java.util.HashMap;
import java.util.Map;

public class BasicDictionaryTypeRegistry implements DictionaryTypeRegistry {
    private final Map<String, DictionaryType> dictionaryTypes = new HashMap<String, DictionaryType>();

    public void register(String name, DictionaryType dictionaryType) {
        dictionaryTypes.put(name, dictionaryType);
    }

    public DictionaryType get(String name) {
        return dictionaryTypes.get(name);
    }
    
}
