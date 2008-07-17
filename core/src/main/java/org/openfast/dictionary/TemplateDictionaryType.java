package org.openfast.dictionary;

public class TemplateDictionaryType implements DictionaryType {
    public Dictionary createDictionary() {
        return new TemplateDictionary();
    }

    public String getName() {
        return "template";
    }
}
