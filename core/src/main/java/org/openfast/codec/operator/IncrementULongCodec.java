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
package org.openfast.codec.operator;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.ULong;
import org.openfast.codec.FieldCodec;
import org.openfast.codec.ULongCodec;
import org.openfast.dictionary.DictionaryEntry;
import org.openfast.error.FastConstants;
import org.openfast.template.operator.DictionaryOperator;

public final class IncrementULongCodec extends DictionaryOperatorULongCodec implements FieldCodec {
    private static final long serialVersionUID = 1L;

    public IncrementULongCodec(DictionaryEntry dictionaryEntry, DictionaryOperator operator, ULongCodec longCodec) {
        super(dictionaryEntry, operator, longCodec);
        
    }

    public void decode(EObject object, int index, ByteBuffer buffer, Context context) {
        if (longCodec.isNull(buffer))
            return;
        ULong value = longCodec.decode(buffer);
        dictionaryEntry.set(value.longValue());
        object.set(index, value);
    }

    public void decodeEmpty(EObject object, int index, Context context) {
        if (dictionaryEntry.isNull()) {
            // leave object value set to null
            dictionaryEntry.setNull();
        } else if (!dictionaryEntry.isDefined()) {
            if (operator.hasDefaultValue()) {
                object.set(index, initialValue);
                dictionaryEntry.set(initialValue);
            } else {
                dictionaryEntry.setNull();
            }
        } else {
            long previousValue = dictionaryEntry.getLong();
            object.set(index, previousValue + 1);
            dictionaryEntry.set(previousValue + 1);
        }
    }

    public int getLength(ByteBuffer buffer) {
        return longCodec.getLength(buffer);
    }

    public int encode(EObject object, int index, byte[] buffer, int offset, Context context) {
        if (!object.isDefined(index)) {
            if (dictionaryEntry.isNull())
                return offset;
            else {
                return encodeNull(buffer, offset, context);
            }
        }
        long value = object.getLong(index);
        if (dictionaryEntry.isNull()) {
            dictionaryEntry.set(value);
            return longCodec.encode(buffer, offset, new ULong(value));
        }
        if (!dictionaryEntry.isDefined()) {
            if (!operator.hasDefaultValue()) {
                dictionaryEntry.set(value);
                return longCodec.encode(buffer, offset, new ULong(value));
            } else if (operator.hasDefaultValue() && value == initialValue) {
                dictionaryEntry.set(value);
                return offset;
            } else {
                return longCodec.encode(buffer, offset, new ULong(value));
            }
        }
        long previousValue = dictionaryEntry.getLong();
        if (value == previousValue + 1) {
            dictionaryEntry.set(value);
            return offset;
        }

        dictionaryEntry.set(value);
        return longCodec.encode(buffer, offset, new ULong(value));
    }

    private int encodeNull(byte[] buffer, int offset, Context context) {
        buffer[offset] = FastConstants.NULL_BYTE;
        dictionaryEntry.setNull();
        return offset + 1;
    }
}