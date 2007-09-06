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


/**
 *
 */
package org.openfast.template.type.codec;

import org.openfast.BitVector;
import org.openfast.BitVectorValue;
import org.openfast.ScalarValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public final class BitVectorType extends TypeCodec {
    private static final long serialVersionUID = 1L;

	BitVectorType() { }

    /**
     * Takes a ScalarValue object, and converts it to a byte array
     * @param value The ScalarValue to be encoded
     * @return Returns a byte array of the passed object
     */
    public byte[] encodeValue(ScalarValue value) {
        return ((BitVectorValue) value).value.getBytes();
    }

    /**
     * Reads in a stream of data and stores it to a BitVector object
     * @param in The InputStream to be decoded
     * @return Returns a new BitVector object with the data stream as an array
     */
    public ScalarValue decode(InputStream in) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int byt;

        do {
            try {
                byt = in.read();

                if (byt < 0) {
                    return null;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            buffer.write(byt);
        } while ((byt & 0x80) == 0);

        return new BitVectorValue(new BitVector(buffer.toByteArray()));
    }

    /**
     * 
     * @return Returns null
     */
    public ScalarValue fromString(String value) {
        return null;
    }

    /**
     * 
     * @return Returns a default BitVectorValue object
     */
    public ScalarValue getDefaultValue() {
        return new BitVectorValue(new BitVector(0));
    }

	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass();
	}
}
