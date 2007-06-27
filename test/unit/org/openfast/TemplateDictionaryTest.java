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

import org.openfast.template.Field;
import org.openfast.template.Group;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;


public class TemplateDictionaryTest extends TestCase {
    public void testTemplateValueLookup() throws Exception {
        Dictionary dictionary = new TemplateDictionary();
        Group template = new MessageTemplate("Position",
                new Field[] {
                    new Scalar("exchange", Type.ASCII_STRING, Operator.COPY, ScalarValue.UNDEFINED, false)
                });
        ScalarValue value = new StringValue("NYSE");
        dictionary.store(template, "exchange", value);

        assertEquals(value, dictionary.lookup(template, "exchange"));

        Group quoteTemplate = new MessageTemplate("Quote",
                new Field[] {
                    new Scalar("bid", Type.DECIMAL, Operator.DELTA, ScalarValue.UNDEFINED, false)
                });
        assertEquals(ScalarValue.UNDEFINED,
            dictionary.lookup(quoteTemplate, "exchange"));
    }

    public void testLookupMultipleValuesForTemplate() throws Exception {
        Dictionary dictionary = new TemplateDictionary();
        Group template = new MessageTemplate("Position",
                new Field[] {
                    new Scalar("exchange", Type.ASCII_STRING, Operator.COPY, ScalarValue.UNDEFINED, false)
                });
        ScalarValue value = new StringValue("NYSE");
        ScalarValue marketValue = new DecimalValue(100000.00);
        dictionary.store(template, "exchange", value);
        dictionary.store(template, "marketValue", marketValue);

        assertFalse(value.equals(ScalarValue.UNDEFINED));
        assertEquals(value, dictionary.lookup(template, "exchange"));
        assertEquals(marketValue, dictionary.lookup(template, "marketValue"));
    }

    public void testReset() {
        Dictionary dictionary = new TemplateDictionary();
        Group template = new MessageTemplate("Position",
                new Field[] {
                    new Scalar("exchange", Type.ASCII_STRING, Operator.COPY, ScalarValue.UNDEFINED, false)
                });
        ScalarValue value = new StringValue("NYSE");
        dictionary.store(template, "exchange", value);

        assertEquals(value, dictionary.lookup(template, "exchange"));
        dictionary.reset();
        assertEquals(ScalarValue.UNDEFINED,
            dictionary.lookup(template, "exchange"));
    }

    public void testExistingTemplateValueLookup() throws Exception {
        Dictionary dictionary = new TemplateDictionary();
        Group template = new MessageTemplate("Position",
                new Field[] {
                    new Scalar("exchange", Type.ASCII_STRING, Operator.COPY, ScalarValue.UNDEFINED, false)
                });
        ScalarValue value = new StringValue("NYSE");
        dictionary.store(template, "exchange", value);

        assertEquals(ScalarValue.UNDEFINED, dictionary.lookup(template, "bid"));
    }
}
