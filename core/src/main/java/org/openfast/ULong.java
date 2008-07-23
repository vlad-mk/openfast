package org.openfast;

import java.math.BigInteger;

/**
 * Represents an unsigned 64-bit integer number.  Callers should
 * check isLarge to prevent data loss.
 * 
 * <blockquote><pre>
 * ULong ulong = FastTypeCodecs.ULONG.decode(buffer, offset);
 * if (ulong.isLarge()) {
 *     BigInteger bigInt = ulong.toBigInteger();
 *     // handle large value cases
 * } else {
 *     long value = ulong.longValue();
 *     // handle regular case
 * }
 * </pre></blockquote>
 * 
 * @author Jacob Northey
 */
public class ULong extends Number {
    private static final long serialVersionUID = 1L;
    private static final long SIGN_BIT = 0x8000000000000000L;
    private final long value;
    public static final long MAX_VALUE = 0xffffffffffffffffL;

    public ULong(long raw) {
        this.value = raw;
    }

    @Override
    public double doubleValue() {
        return longValue();
    }

    @Override
    public float floatValue() {
        return longValue();
    }

    @Override
    public int intValue() {
        return (int) longValue();
    }

    /**
     * Callers should check isLarge to see if the ULong
     * is too large to be contained in a primitive long
     * to prevent data loss.
     * 
     * @return the long value of this ULong. 
     */
    @Override
    public long longValue() {
        return value;
    }
    
    public BigInteger toBigInteger() {
        return new BigInteger(toByteArray());
    }
    
    /**
     * Converts the long value to the two's-complement,
     * big-endian representation.
     * 
     * @return byte array containing the value of this ULong
     */
    public byte[] toByteArray() {
        int size = getSize(value);
        byte[] bytes = new byte[size];
        int index = bytes.length - 1;
        long l = value;
        while (index >= 0) {
            bytes[index] = (byte) (l & 0xff);
            l >>>= 8;
            index--;
        }
        return bytes;
    }

    private static int getSize(long value) {
        if (value < 0) {
            return 9;
        } else if (value <= 0x7f) {
            return 1;
        } else if (value <= 0x7fff) {
            return 2;
        } else if (value <= 0x7fffff) {
            return 3;
        } else if (value <= 0x7fffffff) {
            return 4;
        } else if (value <= 0x7fffffffffL) {
            return 5;
        } else if (value <= 0x7fffffffffffL) {
            return 6;
        } else if (value <= 0x7fffffffffffffL) {
            return 7;
        }
        return 8;
    }

    /**
     * This method should be called before calling {@link #longValue()}
     * 
     * @return true if the value is too large for a primitive long
     */
    public boolean isLarge() {
        return (value & SIGN_BIT) == SIGN_BIT;
    }
    
    @Override
    public String toString() {
        return toBigInteger().toString();
    }
    
    @Override
    public int hashCode() {
        return (int)(value * 37);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !(obj instanceof ULong)) return false;
        return equals((ULong) obj);
    }
    
    public boolean equals(ULong other) {
        return value == other.value;
    }
}
