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
package org.openfast.template.type.codec;

import java.io.InputStream;
import java.util.Date;
import org.openfast.DateValue;
import org.openfast.IntegerValue;
import org.openfast.ScalarValue;
import org.openfast.util.Util;

public class TimeInteger extends TypeCodec {
    private static final long serialVersionUID = 1L;

    public ScalarValue decode(InputStream in) {
        int intValue = ((IntegerValue) TypeCodec.UINT.decode(in)).value;
        int hour = intValue / 10000000;
        intValue -= hour * 10000000;
        int minute = intValue / 100000;
        intValue -= minute * 100000;
        int second = intValue / 1000;
        intValue -= second * 1000;
        int millisecond = intValue % 1000;
        return new DateValue(new Date(hour * 3600000 + minute * 60000 + second * 1000 + millisecond));
    }

    public byte[] encodeValue(ScalarValue value) {
        Date date = ((DateValue) value).value;
        int intValue = Util.timeToInt(date);
        return TypeCodec.UINT.encode(new IntegerValue(intValue));
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass();
    }
}
