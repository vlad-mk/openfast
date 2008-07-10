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
package org.openfast.util;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.openfast.ByteUtil;

public class UtilTest extends TestCase {
    public void testCollectionToString() {
        Map map = new LinkedHashMap();
        map.put("abc", "123");
        map.put("def", "456");
        assertEquals("{abc,def}", Util.collectionToString(map.keySet()));
    }
    
    public void testByteVector() {
        byte[] bytes = ByteUtil.convertBitStringToFastByteArray("11000000 10000001 10000000");
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        buffer.flip();
        putBytes(bytes, buffer);
        assertEquals(192, (int) (buffer.get() & 0xFF));
        assertEquals(129, (int) (buffer.get() & 0xFF));
        assertEquals(128, (int) (buffer.get() & 0xFF));
        assertFalse(buffer.hasRemaining());
        bytes = ByteUtil.convertBitStringToFastByteArray("10000001 10000001 10000001");
        putBytes(bytes, buffer);
        assertEquals(bytes[0], buffer.get());
        assertEquals(bytes[1], buffer.get());
        assertEquals(bytes[2], buffer.get());
        assertFalse(buffer.hasRemaining());
        
    }

    private void putBytes(byte[] bytes, ByteBuffer buffer) {
        if (!buffer.hasRemaining()) {
            buffer.clear();
            buffer.put(bytes);
            buffer.flip();
        }
    }
    public void testIntToTimestamp() {
        Calendar cal = Calendar.getInstance();
        cal.set(2007, 0, 10, 14, 25, 12);
        cal.set(Calendar.MILLISECOND, 253);
        assertEquals(cal.getTime(), Util.toTimestamp(20070110142512253L));
    }
}
