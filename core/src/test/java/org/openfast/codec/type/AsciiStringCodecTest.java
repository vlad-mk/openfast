package org.openfast.codec.type;

import org.openfast.codec.StringCodec;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.test.OpenFastTestCase;

public class AsciiStringCodecTest extends OpenFastTestCase {
    StringCodec ascii = new AsciiStringCodec();
    private byte[] buffer = new byte[10];
    
    public void testDecode() {
        assertEquals("ABCD", decode("01000001 01000010 01000011 11000100"));
        assertEquals("\u0000", decode("00000000 10000000"));
        assertEquals("", decode("10000000"));
    }
    
    public void testOverlong() {
        try {
            decode("00000000 10100001");
            fail();
        } catch (FastException e) {
            assertEquals(FastConstants.R9_STRING_OVERLONG, e.getCode());
        }
    }

    public void testEncode() {
        assertEquals("01000001 01000010 01000011 11000100", encode("ABCD"), 4);
        assertEquals("00000000 10000000", encode("\u0000"), 2);
        assertEquals("10000000", encode(""), 1);
    }
    private byte[] encode(String value) {
        ascii.encode(buffer, 0, value);
        return buffer;
    }
    private String decode(String bits) {
        return ascii.decode(buffer(bits));
    }
}
