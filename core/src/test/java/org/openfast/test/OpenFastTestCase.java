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

package org.openfast.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.openfast.ByteUtil;
import org.openfast.Fast;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.loader.XMLMessageTemplateLoader;

public abstract class OpenFastTestCase extends TestCase {
    public static void assertEquals(String bitString, byte[] encoding) {
        TestUtil.assertBitVectorEquals(bitString, encoding);
    }
    
    public static void assertEquals(String bitString, byte[] encoding, int length) {
        TestUtil.assertBitVectorEquals(bitString, encoding, length);
    }
    
    public static void assertEquals(byte[] expected, byte[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i=0; i<expected.length;i++)
            assertEquals(expected[i], actual[i]);
    }

//    protected static void assertEncodeDecode(ScalarValue value, String bitString, TypeCodec type) {
//        assertEquals(bitString, type.encode(value == null ? ScalarValue.NULL : value));
//        assertEquals(value, type.decode(ByteUtil.createByteStream(bitString)));
//    }

    protected static InputStream bitStream(String bitString) {
        return ByteUtil.createByteStream(bitString);
    }

    protected static InputStream stream(String source) {
        return new ByteArrayInputStream(source.getBytes());
    }

    protected static InputStream byteStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    protected static Date date(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    protected static Date time(int hour, int min, int sec, int ms) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, sec);
        cal.set(Calendar.MILLISECOND, ms);
        return cal.getTime();
    }

    protected byte[] byt(String hexString) {
        return ByteUtil.convertHexStringToByteArray(hexString);
    }

    protected byte[] bytes(String binaryString) {
        return ByteUtil.convertBitStringToFastByteArray(binaryString);
    }

    protected static MessageTemplate template(String templateXml) {
        MessageTemplate[] templates = new XMLMessageTemplateLoader().load(new ByteArrayInputStream(templateXml.getBytes()));
        return templates[0];
    }

    protected MessageTemplate template(Field field) {
        return Fast.SIMPLE.createMessageTemplate("Doesn't matter", new Field[] { field });
    }

    protected InputStream resource(String url) {
        return this.getClass().getClassLoader().getResourceAsStream(url);
    }
    
    protected static void assertEquals(BigDecimal expected, BigDecimal actual) {
        if (expected.compareTo(actual) != 0)
            throw new AssertionFailedError("expected:<" + expected.toPlainString() + "> bug was:<" + actual.toPlainString() + ">");
    }

    protected static MessageTemplate template(Scalar field) {
        MessageTemplate template = Fast.SIMPLE.createMessageTemplate("", new Field[] { field });
        return template;
    }
}
