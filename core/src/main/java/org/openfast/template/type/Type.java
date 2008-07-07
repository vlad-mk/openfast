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
package org.openfast.template.type;

import java.io.Serializable;
import org.lasalletech.exom.QName;

public abstract class Type implements org.openfast.template.Type, Serializable {
    private static final long serialVersionUID = 1L;
    private final QName name;

    public Type(String typeName) {
        this.name = new QName(typeName);
    }
    
    public boolean isRepeating() {
        return false;
    }
    
    public boolean isPrimitive() {
        return true;
    }

    /**
     * 
     * @return Returns name as a string
     */
    public String getName() {
        return name.getName();
    }
    /**
     * @return Returns the name as a string
     */
    public String toString() {
        return name.getName();
    }
    
    public QName getQName() {
        return name;
    }
    public final static Type U8 = new UnsignedIntegerType(8, 256);
    public final static Type U16 = new UnsignedIntegerType(16, 65536);
    public final static Type U32 = new UnsignedIntegerType(32, 4294967295L);
    public final static Type U64 = new UnsignedIntegerType(64, Long.MAX_VALUE);
    public final static Type I8 = new SignedIntegerType(8, Byte.MIN_VALUE, Byte.MAX_VALUE);
    public final static Type I16 = new SignedIntegerType(16, Short.MIN_VALUE, Short.MAX_VALUE);
    public final static Type I32 = new SignedIntegerType(32, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final static Type I64 = new SignedIntegerType(64, Long.MIN_VALUE, Long.MAX_VALUE);
    public final static Type STRING = new StringType("string");
    public final static Type ASCII = new StringType("ascii");
    public final static Type UNICODE = new StringType("unicode");
    public final static Type BYTE_VECTOR = new ByteVectorType();
    public final static Type DECIMAL = new DecimalType();
    public static final Type[] ALL_TYPES = new Type[] { U8, U16, U32, U64, I8, I16, I32, I64, STRING, ASCII, UNICODE, BYTE_VECTOR,
            DECIMAL };
    public static final Type[] INTEGER_TYPES = new Type[] { U8, U16, U32, U64, I8, I16, I32, I64 };

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        return obj.getClass().equals(this.getClass());
    }
    public int hashCode() {
        return name.hashCode();
    }
}
