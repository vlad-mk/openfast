package com.lasalletech.openfast.util;

import java.util.HashMap;
import java.util.Map;

public class BiDirectionalCache<T> implements Cache<T> {
    private final Cache<T> cache;
    private Map<T, Integer> values = new HashMap<T, Integer>();

    public BiDirectionalCache(Cache<T> cache) {
        this.cache = cache;
    }

    public T lookup(int cacheIndex) {
        return cache.lookup(cacheIndex);
    }

    public int store(T value) {
        int index = cache.store(value);
        values.put(value, index);
        return index;
    }
    
    public boolean hasValue(T value) {
        validate(value);
        return values.containsKey(value);
    }
    
    private void validate(T value) {
        if (!values.containsKey(value)) return;
        int index = values.get(value);
        if (!cache.lookup(index).equals(value)) {
            values.remove(value); // invalidate old value
        }
    }

    public int getIndex(T value) {
        validate(value);
        return values.get(value);
    }
}
