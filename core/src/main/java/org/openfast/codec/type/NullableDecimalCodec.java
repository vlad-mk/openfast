package org.openfast.codec.type;

import static org.openfast.codec.type.FastTypeCodecs.NULLABLE_SIGNED_INTEGER;
import static org.openfast.codec.type.FastTypeCodecs.SIGNED_LONG;
import org.openfast.Decimal;
import org.openfast.Global;
import org.openfast.codec.DecimalCodec;
import org.openfast.error.FastConstants;

public class NullableDecimalCodec implements DecimalCodec {
    public Decimal decode(byte[] buffer, int offset) {
        int newOffset = offset + NULLABLE_SIGNED_INTEGER.getLength(buffer, offset);
        int exponent = NULLABLE_SIGNED_INTEGER.decode(buffer, offset);
        if (Math.abs(exponent) > 63) {
            Global.handleError(FastConstants.R1_LARGE_DECIMAL, "Encountered exponent of size " + exponent);
        }
        long mantissa = SIGNED_LONG.decode(buffer, newOffset);
        return new Decimal(mantissa, exponent);
    }

    public int encode(byte[] buffer, int offset, Decimal value) {
        if (Math.abs(value.exponent) > 63) {
            Global.handleError(FastConstants.R1_LARGE_DECIMAL, "Encountered exponent of size " + value.exponent);
        }
        int newOffset = NULLABLE_SIGNED_INTEGER.encode(buffer, offset, value.exponent);
        return SIGNED_LONG.encode(buffer, newOffset, value.mantissa);
    }

    public int getLength(byte[] buffer, int offset) {
        offset += NULLABLE_SIGNED_INTEGER.getLength(buffer, offset);
        return offset + SIGNED_LONG.getLength(buffer, offset);
    }

    public boolean isNull(byte[] buffer, int offset) {
        return false;
    }
}
