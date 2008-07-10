package org.openfast.codec.operator;

import junit.framework.Assert;
import org.lasalletech.exom.QName;
import org.openfast.ByteUtil;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.codec.ScalarCodec;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;
import org.openfast.test.OpenFastTestCase;

public class FastStringOperatorTestHarness {

    public static final QName KEY = new QName("any", "thing");
    public static final String UNDEFINED = "UNDEFINED";
    public static final String NULL = "NULL";
    public static final int NO_INITIAL_VALUE = Integer.MIN_VALUE + 2;
    public static final int INITIAL_VALUE = Integer.MIN_VALUE + 3;
    private final ScalarCodec noDefaultCodec;
    private final ScalarCodec defaultCodec;

    public FastStringOperatorTestHarness(ScalarCodec noDefaultCodec, ScalarCodec defaultCodec) {
        this.noDefaultCodec = noDefaultCodec;
        this.defaultCodec = defaultCodec;
    }

    public void assertDecodeNull(int initialValue, String dictionaryState) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        codec.decodeEmpty(message, 0, null, context);
        Assert.assertFalse(message.isDefined(0));
    }

    public void assertDecodeNull(int initialValue, String dictionaryState, String encoded) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        codec.decode(message, 0, encodedBytes, 0, null, context);
        Assert.assertFalse(message.isDefined(0));
    }

    public void assertDecode(String expectedValue, int initialValue, String dictionaryState) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        codec.decodeEmpty(message, 0, null, context);
        Assert.assertEquals(expectedValue, message.getString(0));
    }

    public void assertDecode(String expectedValue, int initialValue, String dictionaryState, String encoded) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        codec.decode(message, 0, encodedBytes, 0, null, context);
        Assert.assertEquals(expectedValue, message.getString(0));
        
    }
    
    private void initDictionary(Context context, String dictionaryState) {
        if (dictionaryState == NULL) {
            context.getDictionary("global").storeNull(null, KEY, null);
        } else if (dictionaryState != UNDEFINED) {
            context.getDictionary("global").store(null, KEY, null, dictionaryState);
        }
    }

    private ScalarCodec getCodec(int initialValue) {
        ScalarCodec codec;
        if (initialValue == NO_INITIAL_VALUE) {
            codec = noDefaultCodec;
        } else {
            codec = defaultCodec;
        }
        return codec;
    }

    public void assertEncode(String encoded, int initialValue, String dictionaryState) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, null, context);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        Assert.assertEquals(encodedBytes.length, offset);
        OpenFastTestCase.assertEquals(encoded, buffer, offset);
    }
    
    public void assertEncode(String encoded, int initialValue, String dictionaryState, String value) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        message.set(0, value);
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, null, context);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        Assert.assertEquals(encodedBytes.length, offset);
        OpenFastTestCase.assertEquals(encoded, buffer, offset);
    }

}
