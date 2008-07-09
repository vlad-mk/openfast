package org.openfast.codec.operator;

import static org.openfast.codec.operator.FastOperatorTestHarness.INITIAL_VALUE;
import static org.openfast.codec.operator.FastOperatorTestHarness.KEY;
import static org.openfast.codec.operator.FastOperatorTestHarness.NO_INITIAL_VALUE;
import static org.openfast.codec.operator.FastOperatorTestHarness.NULL;
import static org.openfast.codec.operator.FastOperatorTestHarness.UNDEFINED;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.Message;
import org.openfast.codec.type.FastTypeCodecs;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.CopyOperator;
import org.openfast.template.operator.DictionaryOperator;
import org.openfast.template.type.Type;
import org.openfast.test.OpenFastTestCase;

public class CopyIntegerCodecTest extends OpenFastTestCase {
    DictionaryOperator noDefaultOp = new CopyOperator(KEY, Fast.GLOBAL, null);
    CopyIntegerCodec copyNoDefaultCodec = new CopyIntegerCodec(noDefaultOp, FastTypeCodecs.NULLABLE_UNSIGNED_INTEGER);
    Scalar noDefault = new Scalar("", Type.U32, noDefaultOp, true);
    
    DictionaryOperator defaultOp = new CopyOperator(FastOperatorTestHarness.KEY, Fast.GLOBAL, "22");
    CopyIntegerCodec copyDefaultCodec = new CopyIntegerCodec(defaultOp, FastTypeCodecs.NULLABLE_UNSIGNED_INTEGER);
    Scalar defaultScalar = new Scalar("", Type.U32, defaultOp, true);
    
    byte[] buffer = new byte[4];
    Context context = new Context();
    
    public void testDecode() {
        FastOperatorTestHarness harness = new FastOperatorTestHarness(copyNoDefaultCodec, copyDefaultCodec);
        harness.assertDecodeNull( NO_INITIAL_VALUE, UNDEFINED);
        harness.assertDecodeNull( INITIAL_VALUE,    UNDEFINED, "10000000");
        harness.assertDecodeNull( INITIAL_VALUE,    NULL);
        harness.assertDecodeNull( INITIAL_VALUE,    22,        "10000000");
        harness.assertDecode (6,  NO_INITIAL_VALUE, UNDEFINED, "10000111");
        harness.assertDecode (22, INITIAL_VALUE,    UNDEFINED);
        harness.assertDecode (23, INITIAL_VALUE,    UNDEFINED, "10011000");
        harness.assertDecode (23, INITIAL_VALUE,    NULL,      "10011000");
        harness.assertDecode (23, INITIAL_VALUE,    22,        "10011000");
        harness.assertDecode (24, INITIAL_VALUE,    24);
    }

    public void testEncodeNoDefault() {
        MessageTemplate template = template(noDefault);
        Message message = new Message(template);
        
        // Default? | Dictionary State | Field Value
        // NO         UNDEFINED          NULL
        int size = copyNoDefaultCodec.encode(message, 0, buffer, 0, noDefault, context);
        assertEquals("00000000", buffer, 1);
        assertEquals(0, size);
        
        // NO         NULL               NULL
        size = copyNoDefaultCodec.encode(message, 0, buffer, 0, noDefault, context);
        assertEquals(0, size);
        
        // NO         DEFINED            NULL
        context.getDictionary(Fast.GLOBAL).store(null, KEY, null, 2);
        size = copyNoDefaultCodec.encode(message, 0, buffer, 0, noDefault, context);
        assertEquals(1, size);
        assertEquals("10000000", buffer, 1);
        
        // NO         UNDEFINED          DEFINED
        context.reset();
        message.set(0, 5);
        size = copyNoDefaultCodec.encode(message, 0, buffer, 0, noDefault, context);
        assertEquals(1, size);
        assertEquals("10000110", buffer, 1);
        
        // NO         DEFINED            EQUAL DICTIONARY
        size = copyNoDefaultCodec.encode(message, 0, buffer, 0, noDefault, context);
        assertEquals(0, size);
        
        // NO         DEFINED            NOT EQUAL DICTIONARY
        message.set(0, 6);
        size = copyNoDefaultCodec.encode(message, 0, buffer, 0, noDefault, context);
        assertEquals(1, size);
        assertEquals("10000111", buffer, 1);
    }
    
    public void testEncodeWithDefault() {
        MessageTemplate template = template(defaultScalar);
        Message message = new Message(template);
        
        // YES        UNDEFINED          NULL
        int size = copyDefaultCodec.encode(message, 0, buffer, 0, defaultScalar, context);
        assertEquals("10000000", buffer, 1);
        assertEquals(1, size);
        
        // YES        UNDEFINED          EQUAL DEFAULT VALUE
        context.reset();
        message.set(0, 22);
        size = copyDefaultCodec.encode(message, 0, buffer, 0, defaultScalar, context);
        assertEquals(0, size);
        
        // YES        UNDEFINED          NOT EQUAL DEFAULT VALUE
        context.reset();
        message.set(0, 24);
        size = copyDefaultCodec.encode(message, 0, buffer, 0, defaultScalar, context);
        assertEquals(1, size);
        assertEquals("10011001", buffer, 1);
        
        // YES        NULL               DEFINED
        context.getDictionary(defaultOp.getDictionary()).storeNull(null, KEY, null);
        size = copyDefaultCodec.encode(message, 0, buffer, 0, defaultScalar, context);
        assertEquals(1, size);
        assertEquals("10011001", buffer, 1);
        
    }
}
