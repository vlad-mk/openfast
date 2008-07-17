package org.openfast.dictionary;

import java.util.HashMap;
import java.util.Map;

public class BasicDictionaryRegistry implements DictionaryRegistry {
    private final DictionaryTypeRegistry dictionaryTypeRegistry;
    private Map<String, Dictionary> dictionaries = new HashMap<String, Dictionary>();

    public BasicDictionaryRegistry(DictionaryTypeRegistry dictionaryTypeRegistry) {
        this.dictionaryTypeRegistry = dictionaryTypeRegistry;
    }

    public Dictionary get(String name) {
        if (!dictionaries.containsKey(name)) {
            dictionaries.put(name, dictionaryTypeRegistry.get(name).createDictionary());
        }
        return dictionaries.get(name);
    }

    public void reset() {
        for (Dictionary dictionary : dictionaries.values()) {
            dictionary.reset();
        }
    }
}
