package com.lasalletech.openfast.util;

import java.lang.reflect.Array;

public class BasicCache<T> implements Cache<T> {
    private T[] cache;
    private final int size;
    private int index = 0;
    
    public BasicCache(int capacity) {
        this.size = capacity;
    }

    public T lookup(int cacheIndex) {
        return cache[cacheIndex];
    }

    @SuppressWarnings("unchecked")
    public int store(T value) {
        if (value == null) throw new NullPointerException();
        if (cache == null) {
            cache = (T[]) Array.newInstance(value.getClass(), size);
        }
        int nextIndex = index;
        cache[nextIndex] = value;
        index = (index + 1) % size;
        return nextIndex;
    }
}
