package org.openfast;

import java.util.Arrays;
import org.lasalletech.exom.EObject;
import org.lasalletech.exom.Entity;

public class GlobalFastDictionary implements FastDictionary {
    // Use power of 2 for size so that hashes can be calculated quickly
    private Entry[] entries = new Entry[256];
    
    public int lookupInt(Entity template, QName key, QName currentApplicationType) {
        return getEntry(key).getInt();
    }

    public void reset() {
        Arrays.fill(entries, null);
    }

    public void store(Entity template, QName key, QName currentApplicationType, int value) {
        int index = key.hashCode() % entries.length;
        Entry entry = entries[index];
        if (entry == null) {
            entries[index] = new IntegerEntry(key, value);
        } else {
            while (!entry.matches(key)) {
                if (!entry.hasNext()) {
                    entry.setNext(new IntegerEntry(key, value));
                    return;
                }
                entry = entry.getNext();
            }
            entry.setInt(value);
        }
    }

    public void storeNull(Entity entity, QName key, QName currentApplicationType) {
        int index = key.hashCode() & (entries.length - 1);
        Entry entry = entries[index];
        if (entry == null) {
            entries[index] = new NullEntry(key);
        } else {
            while (!entry.matches(key)) {
                if (!entry.hasNext()) {
                    entry.setNext(new NullEntry(key));
                    return;
                }
                entry = entry.getNext();
            }
            entry.setNull();
        }
    }

    public boolean isDefined(EObject object, QName key, QName currentApplicationType) {
        Entry entry = getEntry(key);
        return entry != null;
    }

    private Entry getEntry(QName key) {
        int index = key.hashCode() % entries.length;
        Entry entry = entries[index];
        if (entry == null)
            return null;
        while (!entry.matches(key)) {
            if (!entry.hasNext())
                return null;
            entry = entry.getNext();
        }
        return entry;
    }

    public boolean isNull(EObject object, QName key, QName currentApplicationType) {
        Entry entry = getEntry(key);
        if (entry == null) return false;
        return entry.isNull();
    }
}
