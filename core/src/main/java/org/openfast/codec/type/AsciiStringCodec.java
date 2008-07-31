package org.openfast.codec.type;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import org.openfast.ByteUtil;
import org.openfast.Fast;
import org.openfast.Global;
import org.openfast.codec.StringCodec;
import org.openfast.error.FastConstants;

public class AsciiStringCodec extends StopBitEncodedTypeCodec implements StringCodec {
    private final CharsetDecoder decoder = Charset.forName("US-ASCII").newDecoder();
    private final CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
    public String decode(ByteBuffer bbuf) {
        CharBuffer decoded;
        try {
            int length = getLength(bbuf);
            byte[] buffer = new byte[length];
            bbuf.get(buffer, 0, length);
            if ((buffer[0] & Fast.VALUE_BITS) == 0) {
                if (!ByteUtil.isEmpty(buffer, 0, length)) { 
                    Global.handleError(FastConstants.R9_STRING_OVERLONG, null);
                }
                else if (length > 1 && (buffer[1] & Fast.VALUE_BITS) == 0)
                    return Fast.ZERO_TERMINATOR;
                else
                    return "";
            }
            buffer[length - 1] &= Fast.VALUE_BITS; // remove stop bit
            decoded = decoder.decode(ByteBuffer.wrap(buffer));
            return decoded.toString();
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    public int encode(byte[] buffer, int offset, String value) {
        if (value.length() == 0) {
            buffer[offset] = Fast.NULL;
            return offset + 1;
        }
        if (value.startsWith(Fast.ZERO_TERMINATOR)) {
            buffer[offset] = 0;
            buffer[offset+1] = Fast.STOP_BIT;
            return offset + 2;
        }
        ByteBuffer encoded;
        try {
            encoded = encoder.encode(CharBuffer.wrap(value));
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
        encoded.get(buffer, offset, encoded.limit());
        buffer[encoded.limit() - 1 + offset] |= Fast.STOP_BIT;
        return encoded.limit() + offset;
    }

    public boolean isNull(ByteBuffer buffer) {
        return false;
    }
}
