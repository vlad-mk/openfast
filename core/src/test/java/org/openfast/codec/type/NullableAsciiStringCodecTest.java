package org.openfast.codec.type;

import org.openfast.codec.StringCodec;
import org.openfast.test.OpenFastTestCase;

public class NullableAsciiStringCodecTest extends OpenFastTestCase {
    StringCodec ascii = new NullableAsciiStringCodec();
    private byte[] buffer = new byte[10];
    
    public void testDecode() {
        assertEquals("ABCD", decode("01000001 01000010 01000011 11000100"));
        assertNull(decode("10000000"));
        assertEquals("\u0000", decode("00000000 00000000 10000000"));
        assertEquals("", decode("00000000 10000000"));
    }

    public void testEncode() {
        assertEquals("01000001 01000010 01000011 11000100", encode("ABCD"), 4);
        assertEquals("10000000", encode(null), 1);
        assertEquals("00000000 00000000 10000000", encode("\u0000"), 3);
        assertEquals("00000000 10000000", encode(""), 2);
    }
    private byte[] encode(String value) {
        ascii.encode(buffer, 0, value);
        return buffer;
    }
    private String decode(String bits) {
        return ascii.decode(bytes(bits), 0);
    }
}
