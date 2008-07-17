package org.openfast.dictionary;

public interface DictionaryTypeRegistry {
    void register(String name, DictionaryType dictionaryType);

    DictionaryType get(String name);
}
