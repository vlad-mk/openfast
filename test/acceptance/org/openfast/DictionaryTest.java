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


package org.openfast;

import junit.framework.TestCase;

import org.openfast.session.Session;

import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;
import org.openfast.test.TestUtil;

import java.io.ByteArrayOutputStream;


public class DictionaryTest extends TestCase {
    private Session session;
    private ByteArrayOutputStream out;

    protected void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        session = new Session(null, out);
    }

    public void testMultipleDictionaryTypes() throws Exception {
        Scalar bid = new Scalar("bid", Type.DECIMAL, Operator.COPY, ScalarValue.UNDEFINED, false);
        bid.setDictionary(Dictionary.TEMPLATE);

        MessageTemplate quote = new MessageTemplate("quote", new Field[] { bid });

        Scalar bidR = new Scalar("bid", Type.DECIMAL, Operator.COPY, ScalarValue.UNDEFINED, false);
        MessageTemplate request = new MessageTemplate("request",
                new Field[] { bidR });

        Message quote1 = new Message(quote, 2);
        quote1.setFieldValue(1, new DecimalValue(10.2));

        Message request1 = new Message(request, 1);
        request1.setFieldValue(1, new DecimalValue(10.3));

        Message quote2 = new Message(quote, 2);
        quote2.setFieldValue(1, new DecimalValue(10.2));

        Message request2 = new Message(request, 1);
        request2.setFieldValue(1, new DecimalValue(10.2));

        session.out.registerTemplate(1, request);
        session.out.registerTemplate(2, quote);
        session.out.writeMessage(quote1);
        session.out.writeMessage(request1);
        session.out.writeMessage(quote2);
        session.out.writeMessage(request2);

        String expected = "11100000 10000010 11111111 00000000 11100110 " +
            "11100000 10000001 11111111 00000000 11100111 " +
            "11000000 10000010 " +
            "11100000 10000001 11111111 00000000 11100110";
        TestUtil.assertBitVectorEquals(expected, out.toByteArray());
    }
}
