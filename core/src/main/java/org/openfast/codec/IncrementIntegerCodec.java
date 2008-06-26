/*
The contents of this file are subject to the Mozilla Public License
Version 1.1 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

The Original Code is OpenFAST.

The Initial Developer of the Original Code is The LaSalle Technology
Group, LLC.  Portions created by The LaSalle Technology Group, LLC
are Copyright (C) The LaSalle Technology Group, LLC. All Rights Reserved.

Contributor(s): Jacob Northey <jacob@lasalletech.com>
                Craig Otis <cotis@lasalletech.com>
 */
package org.openfast.codec;

import org.lasalletech.exom.EObject;
import org.openfast.NumericValue;
import org.openfast.QName;
import org.openfast.ScalarValue;
import org.openfast.dictionary.FastDictionary;
import org.openfast.error.FastConstants;
import org.openfast.template.Scalar;

final class IncrementIntegerCodec implements ScalarCodec {
    private static final long serialVersionUID = 1L;
    private final IntegerCodec integerCodec;
    private final int defaultValue;
    private final boolean hasDefaultValue;

    public IncrementIntegerCodec(IntegerCodec integerCodec) {
        this.integerCodec = integerCodec;
        this.hasDefaultValue = false;
        this.defaultValue = 0;
    }

    public IncrementIntegerCodec(IntegerCodec integerCodec, int defaultValue) {
        this.integerCodec = integerCodec;
        this.hasDefaultValue = true;
        this.defaultValue = defaultValue;
    }

    public int decode(EObject object, int index, byte[] buffer, int offset, Scalar scalar, FastDictionary dictionary) {
        int length = integerCodec.getLength(buffer, offset);
        int value = integerCodec.decode(buffer, offset);
        dictionary.store(object.getEntity(), scalar.getKey(), null, value);
        object.set(index, value);
        return offset + length;
    }

    public void decodeEmpty(EObject object, int index, Scalar scalar, FastDictionary dictionary) {
        if (dictionary.isNull(object, scalar.getKey(), null)) {
            // leave object value set to null
            dictionary.storeNull(object.getEntity(), scalar.getKey(), null);
        } else if (!dictionary.isDefined(object, scalar.getKey(), null)) {
            if (hasDefaultValue) {
                object.set(index, defaultValue);
                dictionary.store(object.getEntity(), scalar.getKey(), null, defaultValue);
            } else {
                if (!scalar.isOptional()) {
                    throw new IllegalStateException("Field with operator increment must send a value if no previous value existed.");
                } else {
                    // leave object value set to null
                    dictionary.storeNull(object.getEntity(), scalar.getKey(), null);
                }
            }
        } else {
            int previousValue = dictionary.lookupInt(object.getEntity(), scalar.getKey(), null);
            object.set(index, previousValue + 1);
            dictionary.store(object.getEntity(), scalar.getKey(), null, previousValue + 1);
        }
    }

    public int getLength(byte[] buffer, int offset) {
        return integerCodec.getLength(buffer, offset);
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Scalar scalar, FastDictionary dictionary) {
        QName key = scalar.getKey();
        if (!object.isDefined(index)) {
            if (!scalar.isOptional()) {
                // TODO - error when value is null and scalar is mandatory
            }
            if (dictionary.isNull(object, key, null))
                return offset;
            else {
                return encodeNull(buffer, offset, dictionary, key);
            }
        }
        int value = object.getInt(index);
        if (dictionary.isNull(object, key, null)) {
            dictionary.store(null, key, null, value);
            return integerCodec.encode(buffer, offset, value);
        }
        if (!dictionary.isDefined(object, key, null)) {
            if (!hasDefaultValue) {
                dictionary.store(null, key, null, value);
                return integerCodec.encode(buffer, offset, value);
            } else if (hasDefaultValue && value == defaultValue) {
                return offset;
            } else {
                return integerCodec.encode(buffer, offset, value);
            }
        }
        int previousValue = dictionary.lookupInt(null, key, null);
        if (value == previousValue + 1) {
            return offset;
        }
        return integerCodec.encode(buffer, offset, value);
    }

    private int encodeNull(byte[] buffer, int offset, FastDictionary dictionary, QName key) {
        buffer[offset] = FastConstants.NULL_BYTE;
        dictionary.storeNull(null, key, null);
        return offset + 1;
    }

    public ScalarValue getValueToEncode(ScalarValue value, ScalarValue priorValue, Scalar field) {
        if (priorValue == null) {
            return value;
        }
        if (value == null) {
            if (field.isOptional()) {
                if (priorValue == ScalarValue.UNDEFINED && field.getDefaultValue().isUndefined()) {
                    return null;
                }
                return ScalarValue.NULL;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (priorValue.isUndefined()) {
            if (value.equals(field.getDefaultValue())) {
                return null;
            } else {
                return value;
            }
        }
        if (!value.equals(((NumericValue) priorValue).increment())) {
            return value;
        }
        return null;
    }
}