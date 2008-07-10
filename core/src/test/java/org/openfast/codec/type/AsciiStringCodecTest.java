package org.openfast.codec.type;

import org.openfast.test.OpenFastTestCase;

public class AsciiStringCodecTest extends OpenFastTestCase {
    public void testDecode() {
        byte[] buffer = bytes("01000001 01000010 01000011 11000100");
        assertEquals("ABCD", FastTypeCodecs.ASCII_STRING.decode(buffer, 0));
    }

    public void testEncode() {
        byte[] buffer = new byte[4];
        assertEquals(4, FastTypeCodecs.ASCII_STRING.encode(buffer, 0, "ABCD"));
        assertEquals("01000001 01000010 01000011 11000100", buffer);
    }
}
