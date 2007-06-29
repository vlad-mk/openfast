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


package org.openfast.template;

import junit.framework.TestCase;

import org.openfast.ScalarValue;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;


public class MessageTemplateTest extends TestCase {
    public void testEncodeMessageUsingTemplate() {
        Field[] fields = new Field[2];
        fields[0] = new Scalar("code", Type.ASCII, Operator.COPY, ScalarValue.UNDEFINED, false);
        fields[1] = new Scalar("value", Type.U32, Operator.DELTA,
                ScalarValue.UNDEFINED, false);

        //		MessageTemplate template = new MessageTemplate(null, fields);
    }
}
