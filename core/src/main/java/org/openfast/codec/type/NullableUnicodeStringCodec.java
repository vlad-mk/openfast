package org.openfast.codec.type;

import static org.openfast.codec.type.FastTypeCodecs.NULLABLE_UNSIGNED_INTEGER;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.openfast.Global;
import org.openfast.codec.StringCodec;
import org.openfast.error.FastConstants;

public class NullableUnicodeStringCodec extends LengthEncodedTypeCodec implements StringCodec {
    private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
    private final CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
    public String decode(ByteBuffer buffer) {
        int length = NULLABLE_UNSIGNED_INTEGER.decode(buffer);
        try {
            int previousLimit = buffer.limit();
            buffer.limit(length + buffer.position());
            CharBuffer charBuffer = decoder.decode(buffer);
            buffer.limit(previousLimit);
            return charBuffer.toString();
        } catch (CharacterCodingException e) {
            Global.handleError(FastConstants.GENERAL_ERROR, "Unable to decode unicode string.", e);
            return null;
        }
    }

    public int encode(byte[] buffer, int offset, String value) {
        try {
            ByteBuffer b = encoder.encode(CharBuffer.wrap(value));
            offset = NULLABLE_UNSIGNED_INTEGER.encode(buffer, offset, b.limit());
            b.get(buffer, offset, b.limit());
            return offset + b.limit();
        } catch (CharacterCodingException e) {
            Global.handleError(FastConstants.GENERAL_ERROR, "Unable to decode unicode string.", e);
            return offset;
        }
    }

    public boolean isNull(ByteBuffer buffer) {
        return NULLABLE_UNSIGNED_INTEGER.isNull(buffer);
    }
}
