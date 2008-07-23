package org.openfast;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Decimal extends Number {
    private static final long serialVersionUID = 1L;
    public final int exponent;
    public final long mantissa;

    public Decimal(double value) {
        if (value == 0.0) {
            this.exponent = 0;
            this.mantissa = 0;

            return;
        }

        BigDecimal decimalValue = BigDecimal.valueOf(value);
        int exponent = decimalValue.scale();
        long mantissa = decimalValue.unscaledValue().longValue();

        while (((mantissa % 10) == 0) && (mantissa != 0)) {
            mantissa /= 10;
            exponent -= 1;
        }

        this.mantissa = mantissa;
        this.exponent = -exponent;
    }

    public Decimal(long mantissa, int exponent) {
        this.mantissa = mantissa;
        this.exponent = exponent;
    }

    public Decimal(BigDecimal bigDecimal) {
        this.mantissa = bigDecimal.unscaledValue().longValue();
        this.exponent = bigDecimal.scale();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(BigInteger.valueOf(mantissa), -exponent);
    }
    
    @Override
    public int hashCode() {
        return (exponent * 37 + ((int)mantissa));
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !(obj instanceof Decimal)) return false;
        return equals((Decimal) obj);
    }
    
    private boolean equals(Decimal decimal) {
        return mantissa == decimal.mantissa && exponent == decimal.exponent;
    }
    
    @Override
    public String toString() {
        return toBigDecimal().toPlainString();
    }

    @Override
    public double doubleValue() {
        return mantissa * Math.pow(10.0, exponent);
    }

    @Override
    public float floatValue() {
        return (float) (mantissa * Math.pow(10.0, exponent));
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }
}
