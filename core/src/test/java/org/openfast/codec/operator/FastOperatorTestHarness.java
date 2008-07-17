package org.openfast.codec.operator;

import junit.framework.Assert;
import org.lasalletech.exom.QName;
import org.openfast.ByteUtil;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.Message;
import org.openfast.codec.CodecFactory;
import org.openfast.codec.ScalarCodec;
import org.openfast.dictionary.BasicDictionaryRegistry;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;
import org.openfast.test.OpenFastTestCase;

public class FastOperatorTestHarness {

    public static final QName KEY = new QName("any", "thing");
    public static final int UNDEFINED = Integer.MIN_VALUE;
    public static final int NULL = Integer.MIN_VALUE + 1;
    public static final int NO_INITIAL_VALUE = Integer.MIN_VALUE + 2;
    public static final int INITIAL_VALUE = Integer.MIN_VALUE + 3;
    private final ScalarCodec noDefaultCodec;
    private final ScalarCodec defaultCodec;
    private final Scalar noDefaultScalar;
    private final Scalar defaultScalar;
    private final DictionaryRegistry dictionaryRegistry;

    public FastOperatorTestHarness(Scalar defaultScalar, Scalar noDefaultScalar) {
        CodecFactory factory = FastImplementation.getDefaultVersion().getCodecFactory();
        this.noDefaultScalar = noDefaultScalar;
        this.dictionaryRegistry = new BasicDictionaryRegistry(FastImplementation.getDefaultVersion().getDictionaryTypeRegistry());
        this.noDefaultCodec = factory.createScalarCodec(null, noDefaultScalar, FastImplementation.getDefaultVersion(), dictionaryRegistry);
        this.defaultScalar = defaultScalar;
        this.defaultCodec = factory.createScalarCodec(null, defaultScalar, FastImplementation.getDefaultVersion(), dictionaryRegistry);
    }

    public void assertDecodeNull(int initialValue, int dictionaryState) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(getScalar(initialValue), dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        codec.decodeEmpty(message, 0, null, context);
        Assert.assertFalse(message.isDefined(0));
    }

    public void assertDecodeNull(int initialValue, int dictionaryState, String encoded) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(getScalar(initialValue), dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        codec.decode(message, 0, encodedBytes, 0, null, context);
        Assert.assertFalse(message.isDefined(0));
    }

    public void assertDecode(int expectedValue, int initialValue, int dictionaryState) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(getScalar(initialValue), dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        codec.decodeEmpty(message, 0, null, context);
        Assert.assertEquals(expectedValue, message.getInt(0));
    }

    public void assertDecode(int expectedValue, int initialValue, int dictionaryState, String encoded) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(getScalar(initialValue), dictionaryState);
        Scalar scalar = new Scalar(QName.NULL, Type.U32, null, true);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { scalar });
        Message message = new Message(template);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        codec.decode(message, 0, encodedBytes, 0, null, context);
        Assert.assertEquals(expectedValue, message.getInt(0));
        
    }
    
    private void initDictionary(Scalar scalar, int dictionaryState) {
        dictionaryRegistry.reset();
        DictionaryEntry entry = dictionaryRegistry.get(Fast.GLOBAL).getEntry(scalar);
        if (dictionaryState == NULL) {
            entry.setNull();
        } else if (dictionaryState != UNDEFINED){
            entry.set(dictionaryState);
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

    private Scalar getScalar(int initialValue) {
        if (initialValue == NO_INITIAL_VALUE) {
            return noDefaultScalar;
        }
        return defaultScalar;
    }

    public void assertEncode(String encoded, int initialValue, int dictionaryState) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(getScalar(initialValue), dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, null, context);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        Assert.assertEquals(encodedBytes.length, offset);
        OpenFastTestCase.assertEquals(encoded, buffer, offset);
    }
    
    public void assertEncode(String encoded, int initialValue, int dictionaryState, int value) {
        ScalarCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(getScalar(initialValue), dictionaryState);
        MessageTemplate template = new MessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, Type.U32, null, true) });
        Message message = new Message(template);
        message.set(0, value);
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, null, context);
        OpenFastTestCase.assertEquals(encoded, buffer, offset);
    }

}
