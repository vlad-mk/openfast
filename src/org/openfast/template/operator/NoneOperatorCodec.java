/**
 * 
 */
package org.openfast.template.operator;

import org.openfast.ScalarValue;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;

final class NoneOperatorCodec extends AlwaysPresentOperatorCodec {

	private static final long serialVersionUID = 1L;

	protected NoneOperatorCodec(Operator operator, Type[] types) {
		super(operator, types);
	}

	public ScalarValue getValueToEncode(ScalarValue value,
	    ScalarValue priorValue, Scalar field) {
	    if (value == null) {
	        return ScalarValue.NULL;
	    }
	
	    return value;
	}

	public ScalarValue decodeValue(ScalarValue newValue,
	    ScalarValue previousValue, Scalar field) {
	    return newValue;
	}

	public ScalarValue decodeEmptyValue(ScalarValue previousValue,
	    Scalar field) {
	    throw new IllegalStateException(
	        "This method should never be called.");
	}

	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass();
	}
}