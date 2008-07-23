package org.openfast.codec.type;

import java.util.Arrays;
import org.openfast.Global;
import org.openfast.codec.LongCodec;
import org.openfast.error.ErrorHandler;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.test.OpenFastTestCase;

public class NullableSignedLongCodecTest extends OpenFastTestCase {
    LongCodec codec = new NullableSignedLongCodec();
    byte[] buffer = new byte[10];
    
    public void testOverlongInteger() {
        try {
            decode("00000000 10000001");
            fail();
        } catch (FastException e) {
            assertEquals(FastConstants.R6_OVERLONG_INT, e.getCode());
        }
    }
    
    public void testOverlongIgnored() {
        Global.setErrorHandler(ErrorHandler.NULL);
        assertEquals(1, decode("00000000 10000010"));
        Global.setErrorHandler(ErrorHandler.DEFAULT);
    }
    
    public void testBoundaries() {
        assertEquals("01111111 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 10000000", encode(Long.MIN_VALUE), 10);
        assertEquals("00000001 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000 10000000", encode(Long.MAX_VALUE), 10);
    }
    
    public void testDecode() {
        assertEquals(62, codec.decode(bytes("10111111"), 0));
        assertEquals(63, codec.decode(bytes("00000000 11000000"), 0));
        assertEquals(-1, codec.decode(bytes("11111111"), 0));
        assertEquals(-2, codec.decode(bytes("11111110"), 0));
        assertEquals(-64, codec.decode(bytes("11000000"), 0));
        assertEquals(-65, codec.decode(bytes("01111111 10111111"), 0));
        assertEquals(638, codec.decode(bytes("00000100 11111111"), 0));
        assertEquals(942754, codec.decode(bytes("00111001 01000101 10100011"), 0));
        assertEquals(-942755, codec.decode(bytes("01000110 00111010 11011101"), 0));
        assertEquals(8192, codec.decode(bytes("00000000 01000000 10000001"), 0));
        assertEquals(-8193, codec.decode(bytes("01111111 00111111 11111111"), 0));
    }
    
    public void testEncode() {
        byte[] buffer = new byte[4];
        assertEncode(buffer, "00000000 10111111 00000000 00000000", 62, 2);
        assertEncode(buffer, "00000000 00000000 11000000 00000000", 63, 3);
        assertEncode(buffer, "00000000 11111111 00000000 00000000", -1, 2);
        assertEncode(buffer, "00000000 11111110 00000000 00000000", -2, 2);
        assertEncode(buffer, "00000000 11000000 00000000 00000000", -64, 2);
        assertEncode(buffer, "00000000 01111111 10111111 00000000", -65, 3);
        assertEncode(buffer, "00000000 00000100 11111111 00000000", 638, 3);
        assertEncode(buffer, "00000000 00111001 01000101 10100011", 942754, 4);
        assertEncode(buffer, "00000000 01000110 00111010 11011101", -942755, 4);
        assertEncode(buffer, "00000000 00000000 01000000 10000001", 8192, 4);
        assertEncode(buffer, "00000000 01111111 00111111 11111111", -8193, 4);
    }

    private void assertEncode(byte[] buffer, String bitstring, int value, int expectedOffset) {
        int newOffset = codec.encode(buffer, 1, value);
        assertEquals(bitstring, buffer);
        assertEquals(expectedOffset, newOffset);
        Arrays.fill(buffer, (byte) 0);
    }

    public void testGetLength() {
        assertEquals(1, codec.getLength(bytes("10000000 01010101 10000000"), 0));
        assertEquals(2, codec.getLength(bytes("00100000 10000000 00001111"), 0));
    }

    private long decode(String bits) {
        return codec.decode(bytes(bits), 0);
    }

    private byte[] encode(long value) {
        codec.encode(buffer, 0, value);
        return buffer;
    }
}
