package com.lasalletech.openfast.util;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class LRUCache<T> implements Cache<T> {
    private T[] cache;
    private List<Integer> lru = new LinkedList<Integer>();
    private final int size;
    public LRUCache(int capacity) {
        this.size = capacity;
        for (int i=0; i<capacity; i++)
            lru.add(i);
    }

    public T lookup(int cacheIndex) {
        lru.remove((Integer)cacheIndex);
        lru.add(cacheIndex);
        return cache[cacheIndex];
    }

    @SuppressWarnings("unchecked")
    public int store(T value) {
        if (value == null) throw new NullPointerException();
        if (cache == null) {
            cache = (T[]) Array.newInstance(value.getClass(), size);
        }
        Integer index = lru.remove(0);
        cache[index] = value;
        lru.add(index);
        return index;
    }
}
