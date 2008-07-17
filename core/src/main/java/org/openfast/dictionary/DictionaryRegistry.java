package org.openfast.dictionary;

/**
 * Stores dictionaries for a given FAST Decoder or Encoder.
 * 
 * @author Jacob Northey
 *
 */
public interface DictionaryRegistry {
    Dictionary get(String name);
    void reset();
}
