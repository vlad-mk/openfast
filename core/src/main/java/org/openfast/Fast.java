package org.openfast;

public class Fast {
    public static final byte STOP_BIT = (byte) 0x80;
    public static final byte VALUE_BITS = (byte) 0x7f;
    public static final byte NULL = STOP_BIT;
    
    public static final String ZERO_TERMINATOR = "\u0000";
    
    public static final String GLOBAL = "global";
    public static final String TEMPLATE = "template";
}
