package org.openfast.codec.type;

import static org.openfast.codec.type.FastTypeCodecs.SIGNED_INTEGER;
import static org.openfast.codec.type.FastTypeCodecs.SIGNED_LONG;
import java.nio.ByteBuffer;
import org.openfast.Decimal;
import org.openfast.Global;
import org.openfast.codec.DecimalCodec;
import org.openfast.error.FastConstants;

public class BasicDecimalCodec implements DecimalCodec {
    public Decimal decode(ByteBuffer buffer) {
        int exponent = SIGNED_INTEGER.decode(buffer);
        if (Math.abs(exponent) > 63) {
            Global.handleError(FastConstants.R1_LARGE_DECIMAL, "Encountered exponent of size " + exponent);
        }
        long mantissa = SIGNED_LONG.decode(buffer);
        return new Decimal(mantissa, exponent);
    }

    public int encode(byte[] buffer, int offset, Decimal value) {
        if (Math.abs(value.exponent) > 63) {
            Global.handleError(FastConstants.R1_LARGE_DECIMAL, "Encountered exponent of size " + value.exponent);
        }
        int newOffset = SIGNED_INTEGER.encode(buffer, offset, value.exponent);
        return SIGNED_LONG.encode(buffer, newOffset, value.mantissa);
    }

    public int getLength(ByteBuffer buffer) {
        return SIGNED_INTEGER.getLength(buffer) + SIGNED_LONG.getLength(buffer);
    }

    public boolean isNull(ByteBuffer buffer) {
        return false;
    }
}
