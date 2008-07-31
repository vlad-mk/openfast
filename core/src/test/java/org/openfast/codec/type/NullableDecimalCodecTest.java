package org.openfast.codec.type;

import org.openfast.Decimal;
import org.openfast.codec.DecimalCodec;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.test.OpenFastTestCase;

public class NullableDecimalCodecTest extends OpenFastTestCase {
    DecimalCodec basic = new NullableDecimalCodec();
    byte[] buffer = new byte[12];
    
    public void testBoundaries() {
        assertEquals(new Decimal(Long.MAX_VALUE, 63), decode("00000000 11000000 00000000 01111111 01111111 01111111 01111111 01111111 01111111 01111111 01111111 11111111"));
        assertEquals(new Decimal(Long.MIN_VALUE, -63), decode("11000001 01111111 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 10000000"));
        assertEquals("00000000 11000000 00000000 01111111 01111111 01111111 01111111 01111111 01111111 01111111 01111111 11111111", encode(new Decimal(Long.MAX_VALUE, 63)), 11);
        assertEquals("11000001 01111111 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 10000000", encode(new Decimal(Long.MIN_VALUE, -63)), 11);
    }

    public void testEncodeLargeDecimalReportsError() {
        try {
            basic.encode(buffer, 0, new Decimal(150, 64));
            fail();
        } catch (FastException e) {
            assertEquals(FastConstants.R1_LARGE_DECIMAL, e.getCode());
            assertEquals("Encountered exponent of size 64", e.getMessage());
        }
    }

    public void testDecodeLargeDecimalReportsError() {
        try {
            basic.decode(buffer("00000001 11111111 10000001"));
            fail();
        } catch (FastException e) {
            assertEquals(FastConstants.R1_LARGE_DECIMAL, e.getCode());
            assertEquals("Encountered exponent of size 254", e.getMessage());
        }
    }
    
    public void testDecode() {
        assertEquals(new Decimal(1, 2), decode("10000011 10000001"));
        assertEquals(new Decimal(1453, -3), decode("11111101 00001011 10101101"));
        assertEquals(new Decimal(5, 35), decode("10100100 10000101"));
        assertEquals(new Decimal(942755, 2), decode("10000011 00111001 01000101 10100011"));
        assertEquals(new Decimal(942755, -2), decode("11111110 00111001 01000101 10100011"));
        assertEquals(new Decimal(4, 0), decode("10000001 10000100"));
        assertEquals(new Decimal(4, 2), decode("10000011 10000100"));
        assertEquals(new Decimal(4, -1), decode("11111111 10000100"));
        assertEquals(new Decimal(1, 3), decode("10000100 10000001"));
        assertEquals(new Decimal(9427550, 1), decode("10000010 00000100 00111111 00110100 11011110"));
    }
    public void testEncode() {
        assertEquals("10000011 10000001", encode(new Decimal(1, 2)), 2);
        assertEquals("11111101 00001011 10101101", encode(new Decimal(1453, -3)), 3);
        assertEquals("10100100 10000101", encode(new Decimal(5, 35)), 2);
        assertEquals("10000011 00111001 01000101 10100011", encode(new Decimal(942755, 2)), 4);
        assertEquals("11111110 00111001 01000101 10100011", encode(new Decimal(942755, -2)), 4);
        assertEquals("10000001 10000100", encode(new Decimal(4, 0)), 2);
        assertEquals("10000011 10000100", encode(new Decimal(4, 2)), 2);
        assertEquals("11111111 10000100", encode(new Decimal(4, -1)),2);
        assertEquals("10000100 10000001", encode(new Decimal(1, 3)), 2);
        assertEquals("10000010 00000100 00111111 00110100 11011110", encode(new Decimal(9427550, 1)), 5);
    }
    private byte[] encode(Decimal decimal) {
        basic.encode(buffer, 0, decimal);
        return buffer;
    }
    private Decimal decode(String bits) {
        return basic.decode(buffer(bits));
    }
}
