package org.openfast.codec.type;

import static org.openfast.Fast.STOP_BIT;
import static org.openfast.Fast.VALUE_BITS;
import org.openfast.codec.IntegerCodec;


public class UnsignedIntegerCodec extends StopBitEncodedTypeCodec implements IntegerCodec {
    public int decode(byte[] buffer, int offset) {
        int value = 0;
        int index = offset - 1;
        do {
            index++;
            value = (value << 7) | (buffer[index] & VALUE_BITS);
        } while ((buffer[index] & STOP_BIT) == 0);
        return value;
    }

    public int encode(byte[] buffer, int offset, int value) {
        int size = getUnsignedIntegerSize(value);
        for (int factor = 0; factor < size; factor++) {
            buffer[size - factor - 1 + offset] = (byte) ((value >> (factor * 7)) & VALUE_BITS);
        }
        buffer[offset + size - 1] |= STOP_BIT;
        return offset + size;
    }
    
    /**
     * 
     * @param value
     *            The long to determine the unsigned integer
     * @return Returns an unsigned integer
     */
    public static int getUnsignedIntegerSize(long value) {
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
