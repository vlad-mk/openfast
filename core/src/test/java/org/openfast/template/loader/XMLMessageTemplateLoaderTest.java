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
package org.openfast.template.loader;

import java.io.InputStream;
import org.lasalletech.exom.QName;
import org.openfast.error.ErrorHandler;
import org.openfast.error.FastConstants;
import org.openfast.error.FastException;
import org.openfast.fast.FastOperators;
import org.openfast.fast.FastTypes;
import org.openfast.template.ComposedScalar;
import org.openfast.template.DynamicTemplateReference;
import org.openfast.template.Field;
import org.openfast.template.Group;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Operator;
import org.openfast.template.Scalar;
import org.openfast.template.Sequence;
import org.openfast.template.Type;
import org.openfast.test.OpenFastTestCase;
import org.w3c.dom.Element;

public class XMLMessageTemplateLoaderTest extends OpenFastTestCase {
    private static final String NS = "http://www.fixprotocol.org/ns/templates/sample";

    public void testLoadTemplateWithKey() {
        String templateXml = "<templates xmlns=\"http://www.fixprotocol.org/ns/template-definition\""
                + "   ns=\"http://www.fixprotocol.org/ns/templates/sample\">" + "   <template name=\"SampleTemplate\">"
                + "       <uInt32 name=\"value\"><copy key=\"integer\" /></uInt32>" + "   </template>" + "</templates>";
        MessageTemplate messageTemplate = template(templateXml);
        assertScalarField(messageTemplate, 0, FastTypes.U32, "value", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
    }

    public void testLoadTemplateThatContainsDecimalWithTwinOperators() {
        String templateXml = "<templates xmlns=\"http://www.fixprotocol.org/ns/template-definition\""
                + "	ns=\"http://www.fixprotocol.org/ns/templates/sample\">"
                + "	<template name=\"SampleTemplate\">"
                + "		<decimal name=\"bid\"><mantissa><delta /></mantissa><exponent><copy value=\"-2\" /></exponent></decimal>"
                + "		<decimal name=\"ask\"><mantissa><delta /></mantissa></decimal>"
                + "		<decimal name=\"high\"><exponent><copy/></exponent></decimal>"
                + "		<decimal name=\"low\"><mantissa><delta value=\"10\"/></mantissa><exponent><copy value=\"-2\" /></exponent></decimal>"
                + "		<decimal name=\"open\"><copy /></decimal>" + "		<decimal name=\"close\"><copy /></decimal>" + "	</template>"
                + "</templates>";
        MessageTemplate messageTemplate = template(templateXml);
        assertEquals("SampleTemplate", messageTemplate.getName());
        assertEquals(6, messageTemplate.getFieldCount());
        assertComposedScalarField(messageTemplate, 0, FastTypes.DECIMAL, "bid", FastOperators.COPY, "-2", FastOperators.DELTA, null,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertComposedScalarField(messageTemplate, 1, FastTypes.DECIMAL, "ask", FastOperators.NONE, null, FastOperators.DELTA, null,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertComposedScalarField(messageTemplate, 2, FastTypes.DECIMAL, "high", FastOperators.COPY, null, FastOperators.NONE, null,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertComposedScalarField(messageTemplate, 3, FastTypes.DECIMAL, "low", FastOperators.COPY, "-2", FastOperators.DELTA, "10",
                "http://www.fixprotocol.org/ns/templates/sample");
        assertScalarField(messageTemplate, 4, FastTypes.DECIMAL, "open", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertScalarField(messageTemplate, 5, FastTypes.DECIMAL, "close", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
    }

    public void testLoadTemplateThatContainsGroups() {
        String templateXml = "<templates xmlns=\"http://www.fixprotocol.org/ns/template-definition\""
                + "	ns=\"http://www.fixprotocol.org/ns/templates/sample\">" + "	<template name=\"SampleTemplate\">"
                + "		<group name=\"guy\"><string name=\"First Name\"></string><string name=\"Last Name\"></string></group>"
                + "	</template>" + "</templates>";
        MessageTemplate messageTemplate = template(templateXml);
        assertEquals("SampleTemplate", messageTemplate.getName());
        assertEquals(1, messageTemplate.getFieldCount());
        assertGroup(messageTemplate, 0, "guy");
    }

    public void testLoadTemplateWithUnicodeString() {
        String templateXml = "<templates xmlns=\"http://www.fixprotocol.org/ns/template-definition\""
                + "	ns=\"http://www.fixprotocol.org/ns/templates/sample\">" + "	<template name=\"SampleTemplate\">"
                + "		<string name=\"name\" charset=\"unicode\" presence=\"mandatory\"><copy /></string>"
                + "		<string name=\"id\" charset=\"unicode\" presence=\"optional\"><copy /></string>"
                + "		<string name=\"location\" charset=\"ascii\" presence=\"mandatory\"><copy /></string>"
                + "		<string name=\"id2\" charset=\"ascii\" presence=\"optional\"><copy /></string>" + "	</template>" + "</templates>";
        MessageTemplate messageTemplate = template(templateXml);
        assertScalarField(messageTemplate, 0, FastTypes.UNICODE, "name", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertOptionalScalarField(messageTemplate, 1, FastTypes.UNICODE, "id", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertScalarField(messageTemplate, 2, FastTypes.ASCII, "location", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
        assertOptionalScalarField(messageTemplate, 3, FastTypes.ASCII, "id2", FastOperators.COPY,
                "http://www.fixprotocol.org/ns/templates/sample");
    }

    public void testLoadMdIncrementalRefreshTemplate() {
        InputStream templateStream = resource("FPL/mdIncrementalRefreshTemplate.xml");
        MessageTemplateLoader loader = new XMLMessageTemplateLoader();
        MessageTemplate messageTemplate = loader.load(templateStream)[0];
        assertEquals("MDIncrementalRefresh", messageTemplate.getTypeReference().getName());
        assertEquals("MDRefreshSample", messageTemplate.getName());
        assertEquals(9, messageTemplate.getFieldCount());
        /********************************** TEMPLATE FIELDS **********************************/
        int index = 0;
        assertScalarField(messageTemplate, index++, FastTypes.ASCII, "8", FastOperators.CONSTANT, NS);
        assertScalarField(messageTemplate, index++, FastTypes.U32, "9", FastOperators.CONSTANT, NS);
        assertScalarField(messageTemplate, index++, FastTypes.ASCII, "35", FastOperators.CONSTANT, NS);
        assertScalarField(messageTemplate, index++, FastTypes.ASCII, "49", FastOperators.CONSTANT, NS);
        assertScalarField(messageTemplate, index++, FastTypes.U32, "34", FastOperators.INCREMENT, NS);
        assertScalarField(messageTemplate, index++, FastTypes.ASCII, "52", FastOperators.DELTA, NS);
        assertScalarField(messageTemplate, index++, FastTypes.U32, "75", FastOperators.COPY, NS);
        /************************************* SEQUENCE **************************************/
        assertSequence(messageTemplate, index, "MDEntries", 17, "268", FastOperators.NONE, NS);
        Sequence sequence = (Sequence) messageTemplate.getField(index++);
        assertEquals("MDEntries", sequence.getTypeReference().getName());
        /********************************** SEQUENCE FIELDS **********************************/
        int seqIndex = 0;
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.DECIMAL, "270", FastOperators.DELTA, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.I32, "271", FastOperators.DELTA, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.U32, "273", FastOperators.DELTA, NS);
        assertOptionalScalarField(sequence.getGroup(), seqIndex++, FastTypes.U32, "346", FastOperators.NONE, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.U32, "1023", FastOperators.INCREMENT, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "279", FastOperators.COPY, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "269", FastOperators.COPY, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "107", FastOperators.COPY, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "48", FastOperators.DELTA, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "276", FastOperators.COPY, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "274", FastOperators.COPY, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.DECIMAL, "451", FastOperators.COPY, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "277", FastOperators.DEFAULT, NS);
        assertOptionalScalarField(sequence.getGroup(), seqIndex++, FastTypes.U32, "1020", FastOperators.NONE, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.I32, "537", FastOperators.DEFAULT, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "1024", FastOperators.DEFAULT, NS);
        assertScalarField(sequence.getGroup(), seqIndex++, FastTypes.ASCII, "336", FastOperators.DEFAULT, NS);
        assertScalarField(messageTemplate, index++, FastTypes.ASCII, "10", FastOperators.NONE, NS);
    }

    public void testStaticTemplateReference() {
        String templateXml = "<templates>" + 
        "  <template name=\"t1\">" + 
        "    <string name=\"string\"/>" + 
        "  </template>" + 
        "  <template name=\"t2\">" + 
        "    <uInt32 name=\"quantity\"/>" + 
        "    <templateRef name=\"t1\"/>" + 
        "    <decimal name=\"price\"/>" + 
        "  </template>" + 
        "</templates>";
        MessageTemplate[] templates = new XMLMessageTemplateLoader().load(stream(templateXml));
        assertEquals(3, templates[1].getFieldCount());
        assertScalarField(templates[1], 0, FastTypes.U32, "quantity", FastOperators.NONE, "");
        assertScalarField(templates[1], 1, FastTypes.ASCII, "string", FastOperators.NONE, "");
        assertScalarField(templates[1], 2, FastTypes.DECIMAL, "price", FastOperators.NONE, "");
    }

    public void testNonExistantTemplateReference() {
        String templateXml = "<template name=\"t2\">" + "  <uInt32 name=\"quantity\"/>" + "  <templateRef name=\"t1\"/>"
                + "  <decimal name=\"price\"/>" + "</template>";
         try {
             new XMLMessageTemplateLoader().load(stream(templateXml));
         } catch (FastException e) {
             assertEquals(FastConstants.D8_TEMPLATE_NOT_EXIST, e.getCode());
         }
    }

    public void testReferencedTemplateInOtherLoader() {
        String template1Xml = "<template name=\"t1\">" + "  <string name=\"string\"/>" + "</template>";
        String template2Xml = "<template name=\"t2\">" + "  <uInt32 name=\"quantity\"/>" + "  <templateRef name=\"t1\"/>"
                + "  <decimal name=\"price\"/>" + "</template>";
        MessageTemplateLoader loader1 = new XMLMessageTemplateLoader();
        MessageTemplateLoader loader2 = new XMLMessageTemplateLoader();
        loader2.setTemplateRegistry(loader1.getTemplateRegistry());
        loader1.load(stream(template1Xml));
        MessageTemplate[] templates = loader2.load(stream(template2Xml));
        
        assertEquals(3, templates[0].getFieldCount());
        assertScalarField(templates[0], 0, FastTypes.U32, "quantity", FastOperators.NONE, "");
        assertScalarField(templates[0], 1, FastTypes.ASCII, "string", FastOperators.NONE, "");
        assertScalarField(templates[0], 2, FastTypes.DECIMAL, "price", FastOperators.NONE, "");
    }

    public void testTemplateReferencedFromPreviousLoad() {
        String template1Xml = "<template name=\"t1\">" + "  <string name=\"string\"/>" + "</template>";
        String template2Xml = "<template name=\"t2\">" + "  <uInt32 name=\"quantity\"/>" + "  <templateRef name=\"t1\"/>"
                + "  <decimal name=\"price\"/>" + "</template>";
        MessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.load(stream(template1Xml));
        MessageTemplate[] templates = loader.load(stream(template2Xml));
         assertEquals(3, templates[0].getFieldCount());
         assertScalarField(templates[0], 0, FastTypes.U32, "quantity", FastOperators.NONE, "");
         assertScalarField(templates[0], 1, FastTypes.ASCII, "string", FastOperators.NONE, "");
         assertScalarField(templates[0], 2, FastTypes.DECIMAL, "price", FastOperators.NONE, "");
    }

    public void testDynamicTemplateReference() {
        String template1Xml = "<template name=\"t1\">" + "  <string name=\"string\"/>" + "</template>";
        String template2Xml = "<template name=\"t2\">" + "  <uInt32 name=\"quantity\"/>" + "  <templateRef/>"
                + "  <decimal name=\"price\"/>" + "</template>";
        MessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.load(stream(template1Xml));
        MessageTemplate[] templates = loader.load(stream(template2Xml));
        assertEquals(3, templates[0].getFieldCount());
        assertScalarField(templates[0], 0, FastTypes.U32, "quantity", FastOperators.NONE, "");
        assertScalarField(templates[0], 2, FastTypes.DECIMAL, "price", FastOperators.NONE, "");
        assertTrue(templates[0].getField(1) instanceof DynamicTemplateReference);
    }

    public void testByteVector() {
        String templateXml = "<template name=\"bvt\">" + "  <byteVector name=\"data\">" + "    <length name=\"dataLength\"/>"
                + "    <tail/>" + "  </byteVector>" + "</template>";
        MessageTemplate bvt = template(templateXml);
        assertScalarField(bvt, 0, FastTypes.BYTE_VECTOR, "data", FastOperators.TAIL, "");
    }

    public void testNullDocument() {
        XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.setErrorHandler(ErrorHandler.NULL);
        assertEquals(0, loader.load(null).length);
    }

    public void testCustomFieldParser() {
        String templateXml = "<template name=\"custom\">" + "  <array name=\"intArr\" type=\"int\"></array>" + "</template>";
        XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        try {
            loader.load(stream(templateXml));
        } catch (FastException e) {
             assertEquals("No parser registered for array", e.getMessage());
             assertEquals(FastConstants.PARSE_ERROR, e.getCode());
        }
        loader.addFieldParser(new FieldParser() {
            public boolean canParse(Element element, ParsingContext context) {
                return element.getNodeName().equals("array");
            }

            public Field parse(Element fieldNode, ParsingContext context) {
                return new Array(new QName(fieldNode.getAttribute("name"), ""), false);
            }
        });
         MessageTemplate template = loader.load(stream(templateXml))[0];
         assertEquals(new Array(new QName("intArr", ""), false), template.getField(0));
    }

    private void assertComposedScalarField(MessageTemplate tmpl, int index, Type type, String name, Operator mantissaOperator,
            String mantissaDefaultValue, Operator exponentOperator, String exponentDefaultValue, String namespace) {
        ComposedScalar scalar = (ComposedScalar) tmpl.getField(index);
        assertEquals(new QName(name, namespace), scalar.getQName());
        assertEquals(type, scalar.getType());
        Scalar exponent = scalar.getFields()[0];
        assertEquals(exponentOperator, exponent.getOperator());
        Scalar mantissa = scalar.getFields()[1];
        assertEquals(mantissaOperator, mantissa.getOperator());
    }

    private void assertScalarField(Group messageTemplate, int index, Type type, String name, Operator operator, String namespace) {
        Scalar scalar = messageTemplate.getScalar(index);
        assertEquals(new QName(name, namespace), scalar.getQName());
        assertEquals(operator, scalar.getOperator());
        assertEquals(type, scalar.getType());
        assertFalse(scalar.isOptional());
    }

    private void assertOptionalScalarField(Group messageTemplate, int index, Type type, String name, Operator operator,
            String namespace) {
        Scalar scalar = messageTemplate.getScalar(index);
        assertEquals(new QName(name, namespace), scalar.getQName());
        assertEquals(operator, scalar.getOperator());
        assertEquals(type, scalar.getType());
        assertTrue(scalar.isOptional());
    }

    private void assertGroup(MessageTemplate messageTemplate, int index, String name) {
        Group group = (Group) messageTemplate.getField(index);
        assertEquals(name, group.getName());
    }

    private void assertSequence(MessageTemplate messageTemplate, int index, String name, int fieldCount, String lengthFieldName,
            Operator lengthOperator, String namespace) {
        Sequence sequence = (Sequence) messageTemplate.getField(index);
        assertEquals(fieldCount, sequence.getFieldCount());
        assertEquals(new QName(name, namespace), sequence.getQName());
        if (lengthFieldName != null) {
            assertEquals(lengthFieldName, sequence.getLength().getName());
            assertEquals(lengthOperator, sequence.getLength().getOperator());
        }
    }
}
