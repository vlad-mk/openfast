package org.openfast;

import org.openfast.codec.FastEncoder;
import org.openfast.template.MessageTemplate;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import org.openfast.test.OpenFastTestCase;

public class BasicMessageEncodingAcceptanceTest extends OpenFastTestCase {
    public void testBasicFastImplementation() throws Exception {
        XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.setLoadTemplateIdFromAuxId(true);
        loader.load(resource("acceptance/integerTemplates.xml"));
        MessageTemplate template = loader.getTemplateRegistry().get("incrementInteger");
        
        FastEncoder encoder = new FastEncoder(loader.getTemplateRegistry());
        byte[] buffer = new byte[256];
        Message message = new Message(template);
        message.set(0, 24);
        int size = encoder.encode(buffer, 0, message);
        assertEquals("11100000 10000001 10011000", buffer, 3);
        assertEquals(3, size);
        
        message.set(0, 25);
        size = encoder.encode(buffer, 0, message);
        assertEquals("10000000", buffer, 1);
        assertEquals(1, size);
    }
}
