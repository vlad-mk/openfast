package com.lasalletech.openfast.codec.operator;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.Fast;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.IntegerCodec;
import org.openfast.codec.StringCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.util.BitVectorBuilder;
import org.openfast.util.BitVectorReader;
import com.lasalletech.openfast.template.operator.CacheOperator;
import com.lasalletech.openfast.util.BasicCache;
import com.lasalletech.openfast.util.BiDirectionalCache;
import com.lasalletech.openfast.util.Cache;

public class CacheStringCodec implements FieldCodec {

    private DictionaryEntry entry;
    private StringCodec stringCodec;
    private IntegerCodec integerCodec;
    private CacheOperator operator;

    public CacheStringCodec(CacheOperator operator, DictionaryEntry entry, StringCodec stringCodec, IntegerCodec integerCodec) {
        this.operator = operator;
        this.entry = entry;
        this.integerCodec = integerCodec;
        this.stringCodec = stringCodec;
    }

    @SuppressWarnings("unchecked")
    public void decode(EObject object, int index, ByteBuffer buffer, BitVectorReader pmapReader, Context context) {
        if (!entry.isDefined())
            entry.set(new BasicCache<String>(operator.getSize()));
        Cache<String> cache = (Cache<String>) entry.getObject();
        if (pmapReader.read()) {
            String value = stringCodec.decode(buffer);
            object.set(index, value);
            cache.store(value);
            return;
        } else {
            if (integerCodec.isNull(buffer)) {
                buffer.get(); // read off the null value;
                return;
            }
            int cacheIndex = integerCodec.decode(buffer);
            object.set(index, cache.lookup(cacheIndex));
        }
    }

    @SuppressWarnings("unchecked")
    public int encode(EObject object, int index, byte[] buffer, int offset, BitVectorBuilder pmapBuilder, Context context) {
        if (!object.isDefined(index)) {
            pmapBuilder.skip();
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        String value = object.getString(index);
        if (!entry.isDefined())
            entry.set(new BiDirectionalCache<String>(new BasicCache<String>(operator.getSize())));
        BiDirectionalCache<String> cache = (BiDirectionalCache<String>) entry.getObject();
        if (cache.hasValue(value)) {
            int cacheIndex = cache.getIndex(value);
            pmapBuilder.skip();
            return integerCodec.encode(buffer, offset, cacheIndex);
        } else {
            pmapBuilder.set();
            cache.store(value);
            return stringCodec.encode(buffer, offset, value);
        }
    }

    public int getLength(ByteBuffer buffer, BitVectorReader reader) {
        return 0;
    }
}