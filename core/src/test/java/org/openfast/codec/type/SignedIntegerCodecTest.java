package org.openfast.codec.type;

import java.util.Arrays;
import org.openfast.codec.type.SignedIntegerCodec;
import org.openfast.test.OpenFastTestCase;

public class SignedIntegerCodecTest extends OpenFastTestCase {
    SignedIntegerCodec codec = new SignedIntegerCodec();
    
    public void testDecode() {
        assertEquals(63, codec.decode(bytes("10111111"), 0));
        assertEquals(64, codec.decode(bytes("00000000 11000000"), 0));
        assertEquals(-1, codec.decode(bytes("11111111"), 0));
        assertEquals(-2, codec.decode(bytes("11111110"), 0));
        assertEquals(-64, codec.decode(bytes("11000000"), 0));
        assertEquals(-65, codec.decode(bytes("01111111 10111111"), 0));
        assertEquals(639, codec.decode(bytes("00000100 11111111"), 0));
        assertEquals(942755, codec.decode(bytes("00111001 01000101 10100011"), 0));
        assertEquals(-942755, codec.decode(bytes("01000110 00111010 11011101"), 0));
        assertEquals(8193, codec.decode(bytes("00000000 01000000 10000001"), 0));
        assertEquals(-8193, codec.decode(bytes("01111111 00111111 11111111"), 0));
    }
    
    public void testEncode() {
        byte[] buffer = new byte[4];
        assertEncode(buffer, "00000000 10111111 00000000 00000000", 63, 2);
        assertEncode(buffer, "00000000 00000000 11000000 00000000", 64, 3);
        assertEncode(buffer, "00000000 11111111 00000000 00000000", -1, 2);
        assertEncode(buffer, "00000000 11111110 00000000 00000000", -2, 2);
        assertEncode(buffer, "00000000 11000000 00000000 00000000", -64, 2);
        assertEncode(buffer, "00000000 01111111 10111111 00000000", -65, 3);
        assertEncode(buffer, "00000000 00000100 11111111 00000000", 639, 3);
        assertEncode(buffer, "00000000 00111001 01000101 10100011", 942755, 4);
        assertEncode(buffer, "00000000 01000110 00111010 11011101", -942755, 4);
        assertEncode(buffer, "00000000 00000000 01000000 10000001", 8193, 4);
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
}
