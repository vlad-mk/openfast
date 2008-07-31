package org.openfast.codec.operator;

import java.nio.ByteBuffer;
import junit.framework.Assert;
import org.lasalletech.entity.QName;
import org.openfast.ByteUtil;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.Message;
import org.openfast.codec.SinglePresenceMapEntryFieldCodec;
import org.openfast.dictionary.BasicDictionaryRegistry;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.DictionaryRegistry;
import org.openfast.fast.FastTypes;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.test.OpenFastTestCase;
import org.openfast.util.BitVectorBuilder;

public class FastStringOperatorTestHarness {

    public static final QName KEY = new QName("any", "thing");
    public static final String UNDEFINED = "UNDEFINED";
    public static final String NULL = "NULL";
    public static final int NO_INITIAL_VALUE = Integer.MIN_VALUE + 2;
    public static final int INITIAL_VALUE = Integer.MIN_VALUE + 3;
    private final Scalar noDefaultScalar;
    private final Scalar defaultScalar;
    private final SinglePresenceMapEntryFieldCodec noDefaultCodec;
    private final SinglePresenceMapEntryFieldCodec  defaultCodec;
    private final DictionaryRegistry dictionaryRegistry;
    
    public FastStringOperatorTestHarness(Scalar noDefaultScalar, Scalar defaultScalar) {
        this(noDefaultScalar, defaultScalar, FastImplementation.getDefaultVersion());
    }
    
    public FastStringOperatorTestHarness(Scalar noDefaultScalar, Scalar defaultScalar, FastImplementation implementation) {
        this.dictionaryRegistry = new BasicDictionaryRegistry(FastImplementation.getDefaultVersion().getDictionaryTypeRegistry());
        this.noDefaultScalar = noDefaultScalar;
        this.noDefaultCodec = (SinglePresenceMapEntryFieldCodec) implementation.getCodecFactory().createScalarCodec(null, noDefaultScalar, FastImplementation.getDefaultVersion(), dictionaryRegistry);
        this.defaultScalar = new Scalar(defaultScalar);
        this.defaultCodec = (SinglePresenceMapEntryFieldCodec) implementation.getCodecFactory().createScalarCodec(null, defaultScalar, FastImplementation.getDefaultVersion(), dictionaryRegistry);
    }

    public void assertDecodeNull(int initialValue, String dictionaryState) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        codec.decodeEmpty(message, 0, context);
        Assert.assertFalse(message.isDefined(0));
    }

    public void assertDecodeNull(int initialValue, String dictionaryState, String encoded) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        ByteBuffer encodedBytes = ByteBuffer.wrap(ByteUtil.convertBitStringToFastByteArray(encoded));
        codec.decode(message, 0, encodedBytes, context);
        Assert.assertFalse(message.isDefined(0));
    }

    public void assertDecode(String expectedValue, int initialValue, String dictionaryState) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        codec.decodeEmpty(message, 0, context);
        Assert.assertEquals(expectedValue, message.getString(0));
    }

    public void assertDecode(String expectedValue, int initialValue, String dictionaryState, String encoded) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        ByteBuffer encodedBytes = ByteBuffer.wrap(ByteUtil.convertBitStringToFastByteArray(encoded));
        codec.decode(message, 0, encodedBytes, context);
        Assert.assertEquals(expectedValue, message.getString(0));
        
    }
    
    private void initDictionary(Context context, Scalar scalar, String dictionaryState) {
        dictionaryRegistry.reset();
        DictionaryEntry entry = dictionaryRegistry.get(Fast.GLOBAL).getEntry(scalar);
        if (dictionaryState == NULL) {
            entry.setNull();
        } else if (dictionaryState != UNDEFINED){
            entry.set(dictionaryState);
        }
    }

    private SinglePresenceMapEntryFieldCodec getCodec(int initialValue) {
        SinglePresenceMapEntryFieldCodec codec;
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

    public void assertEncode(String encoded, int initialValue, String dictionaryState) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, new BitVectorBuilder(7), context);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        Assert.assertEquals(encodedBytes.length, offset);
        OpenFastTestCase.assertEquals(encoded, buffer, offset);
    }
    
    public void assertEncode(String encoded, int initialValue, String dictionaryState, String value) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        message.set(0, value);
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, new BitVectorBuilder(7), context);
        byte[] encodedBytes = ByteUtil.convertBitStringToFastByteArray(encoded);
        Assert.assertEquals(encodedBytes.length, offset);
        OpenFastTestCase.assertEquals(encoded, buffer, offset);
    }
    public void assertEncodeEmpty(int initialValue, String dictionaryState, String value) {
        SinglePresenceMapEntryFieldCodec codec = getCodec(initialValue);
        Context context = new Context();
        initDictionary(context, getScalar(initialValue), dictionaryState);
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { new Scalar(QName.NULL, FastTypes.U32, null, true) });
        Message message = template.newObject();
        message.set(0, value);
        byte[] buffer = new byte[32];
        int offset = codec.encode(message, 0, buffer, 0, new BitVectorBuilder(7), context);
        Assert.assertEquals(0, offset);
    }

}
