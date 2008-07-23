package org.openfast.codec.operator;

import junit.framework.TestCase;

public class StringDeltaTest extends TestCase {
    public void testApplyTo() {
        String a = "constantU32.csv";
        String b = "defaultAscii.csv";
        StringDelta delta = StringDelta.diff(b, a);
        assertEquals(-12, delta.getSubtractionLength());
        assertEquals("defaultAscii", delta.getValue());
        
        assertEquals(b, delta.applyTo(a));
        
        delta = StringDelta.diff(a, b);
        assertEquals(-13, delta.getSubtractionLength());
        assertEquals("constantU32", delta.getValue());
        assertEquals(a, delta.applyTo(b));
    }
}
