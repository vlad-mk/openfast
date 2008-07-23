package org.openfast.codec.type;

import static org.openfast.Fast.STOP_BIT;
import static org.openfast.Fast.VALUE_BITS;
import org.openfast.Global;
import org.openfast.codec.LongCodec;
import org.openfast.error.FastConstants;

public class UnsignedLongCodec extends StopBitEncodedTypeCodec implements LongCodec {
    public long decode(byte[] buffer, int offset) {
        if (buffer[offset] == 0)
            Global.handleError(FastConstants.R6_OVERLONG_INT, "Encountered overlong integer.");
        long value = 0;
        int index = offset - 1;
        do {
            index++;
            value = (value << 7) | (buffer[index] & VALUE_BITS);
        } while ((buffer[index] & STOP_BIT) == 0);
        return value;
    }

    public int encode(byte[] buffer, int offset, long value) {
        int size = getUnsignedLongSize(value);
        for (int index = offset + size -1; index >= offset; index--) {
            buffer[index] = (byte) (value & VALUE_BITS);
            value >>>= 7;
        }
        buffer[offset + size - 1] |= STOP_BIT;
        return offset + size;
    }
    
    /**
     * Find the signed integer size for the passed long value
     * 
     * @param value
     *            The long value to be used to get the signed integer size
     * @return Returns an integer of the appropriate signed integer
     */
    public static int getUnsignedLongSize(long value) {
        if (value < 0) {
            return 10;
        }
        if (value < 128) {
            return 1; // 2 ^ 7
        }
        if (value <= 16384) {
            return 2; // 2 ^ 14
        }
        if (value <= 2097152) {
            return 3; // 2 ^ 21
        }
        if (value <= 268435456) {
            return 4; // 2 ^ 28
        }
        if (value <= 34359738368L) {
            return 5; // 2 ^ 35
        }
        if (value <= 4398046511104L) {
            return 6; // 2 ^ 42
        }
        if (value <= 562949953421312L) {
            return 7; // 2 ^ 49
        }
        if (value <= 72057594037927936L) {
            return 8; // 2 ^ 56
        }
        return 9;
    }

    public boolean isNull(byte[] buffer, int offset) {
        return false;
    }
}
