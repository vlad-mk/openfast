package org.openfast.dictionary;

import org.lasalletech.entity.QName;
import org.openfast.template.type.IntegerType;
import org.openfast.template.type.StringType;
import org.openfast.template.type.Type;

public class GlobalFastDictionary implements FastDictionary {
    // Use power of 2 for size so that hashes can be calculated quickly
    private DictionaryEntry[] entries = new DictionaryEntry[256];
    
    public int lookupInt(QName key) {
        return getEntry(key).getInt();
    }

    public void reset() {
        for (DictionaryEntry entry : entries) {
            if (entry != null)
                entry.reset();
        }
    }

    public void store(QName key, int value) {
        int index = key.hashCode() & (entries.length - 1);
        DictionaryEntry entry = entries[index];
        if (entry == null) {
            entries[index] = new IntegerEntry(key, value);
        } else {
            while (!entry.matches(key)) {
                if (!entry.hasNext()) {
                    entry.setNext(new IntegerEntry(key, value));
                    return;
                }
                DictionaryEntry next = entry.getNext();
                entry = next;
            }
            entry.set(value);
        }
    }

    public void storeNull(QName key) {
        int index = key.hashCode() & (entries.length - 1);
        DictionaryEntry entry = entries[index];
        if (entry == null) {
            throw new IllegalStateException("The dictionary entry for " + key + " was never initialized.");
        } else {
            while (!entry.matches(key)) {
                if (!entry.hasNext()) {
                    throw new IllegalStateException("The dictionary entry for " + key + " was never initialized.");
                }
                entry = entry.getNext();
            }
            entry.setNull();
        }
    }

    public boolean isDefined(QName key) {
        DictionaryEntry entry = getEntry(key);
        return entry != null;
    }

    public DictionaryEntry getEntry(QName key) {
        int index = key.hashCode() & (entries.length - 1);
        DictionaryEntry entry = entries[index];
        if (entry == null)
            return null;
        while (!entry.matches(key)) {
            if (!entry.hasNext())
                return null;
            entry = entry.getNext();
        }
        return entry;
    }

    public boolean isNull(QName key) {
        DictionaryEntry entry = getEntry(key);
        if (entry == null) return false;
        return entry.isNull();
    }

    public String lookupString(QName key) {
        throw new UnsupportedOperationException();
    }

    public void store(QName key, String value) {
        throw new UnsupportedOperationException();
    }

    public DictionaryEntry getEntry(QName key, Type type) {
        DictionaryEntry entry = getEntry(key);
        if (entry == null) {
            entry = createEntry(key, type);
            storeEntry(entry);
        }
        return entry;
    }

    private DictionaryEntry createEntry(QName key, Type type) {
        if (type instanceof IntegerType) {
            return new IntegerEntry(key);
        } else if (type instanceof StringType) {
            return new StringEntry(key);
        }
        return null;
    }

    private void storeEntry(DictionaryEntry e) {
        int index = e.getKey().hashCode() & (entries.length - 1);
        DictionaryEntry entry = entries[index];
        if (entry == null) {
            entries[index] = e;
        } else {
            while (entry.hasNext()) {
                entry = entry.getNext();
            }
            entry.setNext(entry);
        }
    }
}
