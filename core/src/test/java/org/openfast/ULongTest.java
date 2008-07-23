package org.openfast;

import java.math.BigInteger;
import org.openfast.test.OpenFastTestCase;

public class ULongTest extends OpenFastTestCase {
    public void testIntValue() {
        assertEquals(Integer.MAX_VALUE, new ULong(0x7fffffff).intValue());
    }

    public void testLongValue() {
        assertEquals(Long.MAX_VALUE, new ULong(0x7fffffffffffffffL).longValue());
    }

    public void testToString() {
        assertEquals("18446744073709551615", new ULong(0xffffffffffffffffL).toString());
        assertEquals("64", new ULong(64).toString());
    }
    public void testToBigInteger() {
        assertEquals(new BigInteger("18446744073709551615"), new ULong(0xffffffffffffffffL).toBigInteger());
    }

    public void testToByteArray() {
        assertEquals("00000000 11111111 11111111 11111111 11111111 11111111 11111111 11111111 11111111", new ULong(0xffffffffffffffffL).toByteArray());
        assertEquals("01000000", new ULong(64).toByteArray());
    }

    public void testIsLarge() {
        assertTrue(new ULong(0xffffffffffffffffL).isLarge());
        assertFalse(new ULong(0x7fffffffffffffffL).isLarge());
        assertTrue(new ULong(0x8000000000000000L).isLarge());
    }
}
