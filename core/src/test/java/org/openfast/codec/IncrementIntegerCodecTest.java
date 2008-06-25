package org.openfast.codec;

import org.lasalletech.exom.EObject;
import org.lasalletech.exom.simple.SimpleEntity;
import org.lasalletech.exom.simple.SimpleField;
import org.openfast.ByteUtil;
import org.openfast.FastDictionary;
import org.openfast.GlobalFastDictionary;
import org.openfast.ScalarValue;
import org.openfast.template.Scalar;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;
import org.openfast.test.OpenFastTestCase;

public class IncrementIntegerCodecTest extends OpenFastTestCase {
    IncrementIntegerCodec noDefaultSignedCodec = new IncrementIntegerCodec(new SignedIntegerCodec());
    IncrementIntegerCodec default22SignedCodec = new IncrementIntegerCodec(new SignedIntegerCodec(), 22);
    
    public void testDecodeEmpty() {
        SimpleEntity entity = new SimpleEntity("yeah");
        entity.add(new SimpleField("anything", null));
        EObject object = entity.newObject();
        Scalar scalar = new Scalar("1", Type.U32, Operator.INCREMENT, ScalarValue.UNDEFINED, true);
        FastDictionary dictionary = new GlobalFastDictionary();
        
        noDefaultSignedCodec.decodeEmpty(object, 0, scalar, dictionary);
        assertFalse(object.isDefined(0));
        
        dictionary.reset();
        default22SignedCodec.decodeEmpty(object, 0, scalar, dictionary);
        assertEquals(22, object.getInt(0));
        
        default22SignedCodec.decodeEmpty(object, 0, scalar, dictionary);
        assertEquals(23, object.getInt(0));
    }

    public void testDecode() {
    }

    public void testGetLength() {
        byte[] buffer = ByteUtil.convertBitStringToFastByteArray("01000000 10000000");
        assertEquals(2, noDefaultSignedCodec.getLength(buffer, 0));
    }

    public void testEncode() {
        
    }
}
