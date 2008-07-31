package com.lasalletech.openfast.codec.operator;

import static org.openfast.codec.type.FastTypeCodecs.ASCII_STRING;
import static org.openfast.codec.type.FastTypeCodecs.NULLABLE_UNSIGNED_INTEGER;
import java.nio.ByteBuffer;
import org.lasalletech.entity.QName;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.Message;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.dictionary.ObjectEntry;
import org.openfast.fast.FastTypes;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.test.OpenFastTestCase;
import org.openfast.util.BitVector;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;
import com.lasalletech.openfast.template.operator.CacheOperator;

public class CacheStringCodecTest extends OpenFastTestCase {
    Context context = new Context();
    QName key = new QName("values");
    CacheOperator operator = new CacheOperator(key, 256);
    DictionaryEntry entry = new ObjectEntry(key);
    CacheStringCodec nullable = new CacheStringCodec(operator, entry, ASCII_STRING, NULLABLE_UNSIGNED_INTEGER);
    Scalar field = new Scalar(QName.NULL, FastTypes.ASCII, operator, true);
    MessageTemplate template = Fast.SIMPLE.createMessageTemplate(QName.NULL, new Field[] { field });
    public void testDecode() {
        Message message = template.newObject();
        ByteBuffer encodedBytes = buffer("11000001");
        BitVectorReader reader = new BitVectorReader(new BitVector(bytes("11000000")));
        nullable.decode(message, 0, encodedBytes, reader, context);
        assertEquals("A", message.getString(0));
        
        message = template.newObject();
        reader = new BitVectorReader(new BitVector(bytes("10000000")));
        encodedBytes = buffer("10000001");
        nullable.decode(message, 0, encodedBytes, reader, context);
        assertEquals("A", message.getString(0));
    }

    public void testEncode() {
        Message message = template.newObject();
        message.set(0, "A");
        byte[] buffer = new byte[10];
        BitVectorBuilder pmapBuilder = new BitVectorBuilder(7);
        nullable.encode(message, 0, buffer, 0, pmapBuilder, context);
        assertEquals("11000000", pmapBuilder.getBitVector().getBytes());
        assertEquals("11000001", buffer, 1);
        
        pmapBuilder = new BitVectorBuilder(7);
        nullable.encode(message, 0, buffer, 0, pmapBuilder, context);
        assertEquals("10000000", pmapBuilder.getBitVector().getBytes());
        assertEquals("10000001", buffer, 1);
    }
}
