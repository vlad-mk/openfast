package com.lasalletech.openfast.util;

import junit.framework.TestCase;

public class LRUCacheTest extends TestCase {
    public void testStore() {
        LRUCache<String> cache = new LRUCache<String>(2);
        cache.store("abcd");
        cache.store("efgh");
        assertEquals("abcd", cache.lookup(0));
        assertEquals("efgh", cache.lookup(1));
        cache.store("wxyz");
        assertEquals("wxyz", cache.lookup(0));
    }
}
