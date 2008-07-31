package com.lasalletech.openfast.util;

public interface Cache<T> {
    /**
     * Stores a value in this cache.  Implementations are responsible for 
     * determining which index to store the value in based on the 
     * Cache algorithm.
     * 
     * @param value the value to store
     * @return the index in the cache where the value is stored
     */
    public int store(T value);
    public T lookup(int cacheIndex);
}
