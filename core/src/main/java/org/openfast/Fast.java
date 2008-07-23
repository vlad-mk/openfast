package org.openfast;

import org.openfast.simple.SimpleMessageTemplateFactory;

public class Fast {
    public static final byte STOP_BIT = (byte) 0x80;
    public static final byte VALUE_BITS = (byte) 0x7f;
    public static final byte NULL = STOP_BIT;
    
    public static final String ZERO_TERMINATOR = "\u0000";
    
    public static final String GLOBAL = "global";
    public static final String TEMPLATE = "template";
    public static final MessageTemplateFactory SIMPLE = new SimpleMessageTemplateFactory();
    public static final String SCP_1_1_NAMESPACE = "http://www.fixprotocol.org/ns/fast/scp/1.1";
    public static final byte SIGN_BIT = 0x40;
}
