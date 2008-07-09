package org.openfast.codec.operator;

import org.lasalletech.exom.EObject;
import org.lasalletech.exom.simple.SimpleEntity;
import org.lasalletech.exom.simple.SimpleField;
import org.openfast.ByteUtil;
import org.openfast.Context;
import org.openfast.codec.operator.IncrementIntegerCodec;
import org.openfast.codec.type.SignedIntegerCodec;
import org.openfast.dictionary.FastDictionary;
import org.openfast.dictionary.GlobalFastDictionary;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;
import org.openfast.test.OpenFastTestCase;

public class IncrementIntegerCodecTest extends OpenFastTestCase {
    IncrementIntegerCodec noDefaultSignedCodec = new IncrementIntegerCodec(new SignedIntegerCodec());
    IncrementIntegerCodec default22SignedCodec = new IncrementIntegerCodec(new SignedIntegerCodec(), 22);

    public void testEncode() {
        SimpleEntity entity = new SimpleEntity("yeah");
        entity.add(new SimpleField("anything", null));
        EObject object = entity.newObject();
        Scalar scalar = new Scalar("1", Type.U32, null, true);
        Context context = new Context();
        byte[] buffer = new byte[1];
        
        object.set(0, 22);
        int newOffset = default22SignedCodec.encode(object, 0, buffer, 0, scalar, context);
        assertEquals(0, newOffset);
        
        context.reset();
        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
        assertEquals(1, newOffset);
        assertEquals("10010110", buffer);
        
        object.set(0, 23);
        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
        assertEquals(0, newOffset);
        
        object.set(0, 25);
        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
        assertEquals(1, newOffset);
        assertEquals("10011001", buffer);
        
        object.clear(0);
        newOffset = noDefaultSignedCodec.encode(object, 0, buffer, 0, scalar, context);
        assertEquals(1, newOffset);
        assertEquals("10000000", buffer);
    }
    
    public void testDecodeEmpty() {
        SimpleEntity entity = new SimpleEntity("yeah");
        entity.add(new SimpleField("anything", null));
        EObject object = entity.newObject();
        Scalar scalar = new Scalar("1", Type.U32, null, true);
        Context context = new Context();
        
        noDefaultSignedCodec.decodeEmpty(object, 0, scalar, context);
        assertFalse(object.isDefined(0));
        
        context.reset();
        default22SignedCodec.decodeEmpty(object, 0, scalar, context);
        assertEquals(22, object.getInt(0));
        
        default22SignedCodec.decodeEmpty(object, 0, scalar, context);
        assertEquals(23, object.getInt(0));
    }

    public void testDecode() {
        SimpleEntity entity = new SimpleEntity("yeah");
        entity.add(new SimpleField("anything", null));
        EObject object = entity.newObject();
        Scalar scalar = new Scalar("1", Type.U32, null, true);
        FastDictionary dictionary = new GlobalFastDictionary();
        
        noDefaultSignedCodec.decode(object, 0, bytes("10000001"), 0, scalar, new Context());
        assertEquals(1, object.getInt(0));
    }

    public void testGetLength() {
        byte[] buffer = ByteUtil.convertBitStringToFastByteArray("01000000 10000000");
        assertEquals(2, noDefaultSignedCodec.getLength(buffer, 0));
    }
}
