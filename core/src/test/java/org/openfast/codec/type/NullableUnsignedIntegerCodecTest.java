package org.openfast.codec.type;

import org.openfast.Global;
import org.openfast.codec.IntegerCodec;
import org.openfast.error.ErrorHandler;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.test.OpenFastTestCase;

public class NullableUnsignedIntegerCodecTest extends OpenFastTestCase {
    IntegerCodec codec = new NullableUnsignedIntegerCodec();
    private byte[] buffer = new byte[10];
    
    public void testBoundaries() {
        assertEquals(Integer.MAX_VALUE, decode("00001000 00000000 00000000 00000000 10000000"));
        assertEquals("00001000 00000000 00000000 00000000 10000000", encode(Integer.MAX_VALUE), 5);
    }
    
    public void testOverlong() {
        try {
            decode("00000000 11000001");
            fail();
        } catch (FastException e) {
            assertEquals(FastConstants.R6_OVERLONG_INT, e.getCode());
        }
    }
    
    public void testOverlongIgnored() {
        Global.setErrorHandler(ErrorHandler.NULL);
        assertEquals(64, decode("00000000 11000001"));
        Global.setErrorHandler(ErrorHandler.DEFAULT);
    }
    
    public void testDecode() {
        assertEquals(1, decode("10000010"));
        assertEquals(2, decode("10000011"));
    }

    public void testEncode() {
        assertEquals("10000010", encode(1), 1);
        assertEquals("10000011", encode(2), 1);
    }

    private int decode(String bits) {
        return codec.decode(bytes(bits), 0);
    }

    private byte[] encode(int value) {
        codec.encode(buffer, 0, value);
        return buffer;
    }
}
