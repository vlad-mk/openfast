/**
 * 
 */
package org.openfast.template.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.openfast.DecimalValue;
import org.openfast.IntegerValue;
import org.openfast.ScalarValue;
import org.openfast.error.FastConstants;

final class SingleFieldDecimal extends Type {
	SingleFieldDecimal() {
		super(DECIMAL, "single field scaled number", new String[] { "decimal" });
	}

	public byte[] encodeValue(ScalarValue v) {
		if (v == ScalarValue.NULL) return Type.NULL_VALUE_ENCODING;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		DecimalValue value = (DecimalValue) v;
		try {
			if (Math.abs(value.exponent) > 63)
				FastConstants.handleError(FastConstants.LARGE_DECIMAL, "Encountered exponent of size " + value.exponent);
			buffer.write(Type.INTEGER.encode(new IntegerValue(value.exponent)));
			buffer.write(Type.INTEGER.encode(new IntegerValue(value.mantissa)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return buffer.toByteArray();
	}

	public ScalarValue decode(InputStream in) {
		int exponent = ((IntegerValue) Type.INTEGER.decode(in)).value;
		if (Math.abs(exponent) > 63)
			FastConstants.handleError(FastConstants.LARGE_DECIMAL, "Encountered exponent of size " + exponent);
		if (exponent == Type.NULL_SCALED_NUMBER) return DecimalValue.NULL;
		int mantissa = ((IntegerValue) Type.INTEGER.decode(in)).value;
		DecimalValue decimalValue = new DecimalValue(mantissa, exponent);
		return decimalValue;
	}
	
	public ScalarValue parse(String value) {
		return new DecimalValue(Double.parseDouble(value));
	}

	public ScalarValue getDefaultValue() {
		return new DecimalValue(0.0);
	}
}