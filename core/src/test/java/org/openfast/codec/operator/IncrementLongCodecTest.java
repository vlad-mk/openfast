package org.openfast.codec.operator;

import static org.openfast.Fast.GLOBAL;
import static org.openfast.codec.operator.FastLongOperatorTestHarness.INITIAL_VALUE;
import static org.openfast.codec.operator.FastLongOperatorTestHarness.UNDEFINED;
import static org.openfast.codec.operator.FastOperatorTestHarness.KEY;
import static org.openfast.fast.FastTypes.U32;
import static org.openfast.template.ScalarBuilder.scalar;
import org.openfast.fast.FastTypes;
import org.openfast.template.Scalar;
import org.openfast.test.OpenFastTestCase;

public class IncrementLongCodecTest extends OpenFastTestCase {
    Scalar noDefaultScalar = scalar("noDefault", U32).increment(KEY, GLOBAL, null).optional().build();
    Scalar defaultScalar   = scalar("default",   U32).increment(KEY, GLOBAL, "22").optional().build();
    FastLongOperatorTestHarness harness = new FastLongOperatorTestHarness(defaultScalar, noDefaultScalar);
    public void testEncode() {
        harness.assertEncode("10000000", INITIAL_VALUE, UNDEFINED);
//        SimpleEntity entity = new SimpleEntity("yeah");
//        entity.add(new SimpleField("anything", null));
//        EObject object = entity.newObject();
//        Scalar scalar = new Scalar("1", AbstractType.U32, null, true);
//        Context context = new Context();
//        byte[] buffer = new byte[1];
//        
//        object.set(0, 22);
//        int newOffset = default22SignedCodec.encode(object, 0, buffer, 0, scalar, context);
//        assertEquals(0, newOffset);
//        
//        context.reset();
//        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
//        assertEquals(1, newOffset);
//        assertEquals("10010110", buffer);
//        
//        object.set(0, 23);
//        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
//        assertEquals(0, newOffset);
//        
//        object.set(0, 25);
//        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
//        assertEquals(1, newOffset);
//        assertEquals("10011001", buffer);
//        
//        object.clear(0);
//        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
//        assertEquals(1, newOffset);
//        assertEquals("10000000", buffer);
    }
    
    public void testDecodeEmpty() {
//        SimpleEntity entity = new SimpleEntity("yeah");
//        entity.add(new SimpleField("anything", null));
//        EObject object = entity.newObject();
//        Scalar scalar = new Scalar("1", AbstractType.U32, null, true);
//        Context context = new Context();
//        
//        noDefaultSignedCodec.decodeEmpty(object, 0, scalar, context);
//        assertFalse(object.isDefined(0));
//        
//        context.reset();
//        default22SignedCodec.decodeEmpty(object, 0, scalar, context);
//        assertEquals(22, object.getInt(0));
//        
//        default22SignedCodec.decodeEmpty(object, 0, scalar, context);
//        assertEquals(23, object.getInt(0));
    }

    public void testDecode() {
//        SimpleEntity entity = new SimpleEntity("yeah");
//        entity.add(new SimpleField("anything", null));
//        EObject object = entity.newObject();
//        Scalar scalar = new Scalar("1", AbstractType.U32, null, true);
//        FastDictionary dictionary = new BasicFastDictionary();
//        
//        noDefaultSignedCodec.decode(object, 0, bytes("10000001"), 0, scalar, new Context());
//        assertEquals(1, object.getInt(0));
    }

    public void testGetLength() {
//        byte[] buffer = ByteUtil.convertBitStringToFastByteArray("01000000 10000000");
//        assertEquals(2, noDefaultSignedCodec.getLength(buffer, 0));
    }
}
