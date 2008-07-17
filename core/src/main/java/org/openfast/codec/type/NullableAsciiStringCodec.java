package org.openfast.codec.type;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import org.openfast.ByteUtil;
import org.openfast.Fast;
import org.openfast.Global;
import org.openfast.codec.StringCodec;
import org.openfast.error.FastConstants;

public class NullableAsciiStringCodec extends StopBitEncodedTypeCodec implements StringCodec {
    private final CharsetDecoder decoder = Charset.forName("US-ASCII").newDecoder();
//    private final CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
    public String decode(byte[] buffer, int offset) {
        CharBuffer decoded;
        try {
            int length = getLength(buffer, offset);
            if ((buffer[offset] & Fast.VALUE_BITS) == 0) {
                if (!ByteUtil.isEmpty(buffer, offset, length))
                    Global.handleError(FastConstants.R9_STRING_OVERLONG, null);
                if (length > 1 && (buffer[offset+1] & Fast.VALUE_BITS) == 0)
                    return Fast.ZERO_TERMINATOR;
                return "";
            }
            buffer[length + offset - 1] &= Fast.VALUE_BITS; // remove stop bit
            decoded = decoder.decode(ByteBuffer.wrap(buffer, offset, length));
            buffer[length + offset - 1] |= Fast.STOP_BIT; // replace stop bit to prevent side effects
            return decoded.toString();
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    public int encode(byte[] buffer, int offset, String value) {
        return 0;
    }

    public boolean isNull(byte[] buffer, int offset) {
        return buffer[offset] == Fast.NULL;
    }
}
