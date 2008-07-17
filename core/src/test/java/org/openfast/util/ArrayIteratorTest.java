package org.openfast.util;

import static org.openfast.util.Util.iterator;
import junit.framework.TestCase;

public class ArrayIteratorTest extends TestCase {
    public void testNext() {
        String[] values = new String[] { "a", "b", "c" };
        ArrayIterator<String> iter = iterator(values);
        assertTrue(iter.hasNext());
        assertEquals(values[0], iter.next());
        assertTrue(iter.hasNext());
        assertEquals(values[1], iter.next());
        assertTrue(iter.hasNext());
        assertEquals(values[2], iter.next());
        assertFalse(iter.hasNext());
    }

    public void testRemove() {
        try {
            iterator(new String[] { "a" }).remove();
            fail();
        } catch (UnsupportedOperationException e) {}
    }
}
