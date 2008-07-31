package org.openfast.codec.type;

import org.openfast.codec.StringCodec;
import org.openfast.test.OpenFastTestCase;

public class NullableUnicodeStringCodecTest extends OpenFastTestCase {
    StringCodec codec = new NullableUnicodeStringCodec();
    private byte[] buffer = new byte[10];
    
    public void testDecode() {
        assertEquals(new String("\u03B1\u03B2\u03B3"), decode("10000111 11001110 10110001 11001110 10110010 11001110 10110011"));
        assertEquals(new String("abc"), decode("10000100 01100001 01100010 01100011"));
    }

    private String decode(String bits) {
        return codec.decode(buffer(bits));
    }

    public void testEncode() {
        assertEquals("10000111 11001110 10110001 11001110 10110010 11001110 10110011", encode(new String("\u03B1\u03B2\u03B3")), 7);
        assertEquals("10000100 01100001 01100010 01100011", encode(new String("abc")), 4);
    }

    private byte[] encode(String value) {
        codec.encode(buffer, 0, value);
        return buffer;
    }
}
