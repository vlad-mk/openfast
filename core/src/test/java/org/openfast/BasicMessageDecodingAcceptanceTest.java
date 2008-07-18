package org.openfast;

import org.openfast.codec.FastDecoder;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import org.openfast.test.OpenFastTestCase;

public class BasicMessageDecodingAcceptanceTest extends OpenFastTestCase {
    public void testBasicMessageDecoding() {
        XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.setLoadTemplateIdFromAuxId(true);
        loader.load(resource("acceptance/integerTemplates.xml"));
        
        FastDecoder decoder = new FastDecoder(loader.getTemplateRegistry());
        byte[] buffer = ByteUtil.convertBitStringToFastByteArray("11100000 10000001 10011000");
        Message message = decoder.decode(buffer, 0);
        assertEquals(3, decoder.getNextMessageLength(buffer, 0));
        assertEquals(24, message.getInt(0));
    }
}
