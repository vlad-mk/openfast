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

import org.lasalletech.exom.QName;
import org.openfast.template.Field;
import org.openfast.template.Operator;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;
import org.w3c.dom.Element;

public class ScalarParser extends AbstractFieldParser {
    public ScalarParser(String[] nodeNames) {
        super(nodeNames);
    }

    public ScalarParser(String nodeName) {
        super(nodeName);
    }

    public ScalarParser() {
        super(new String[] {});
    }

    public boolean canParse(Element element, ParsingContext context) {
        return context.getTypeRegistry().isDefined(getTypeName(element));
    }

    public Field parse(Element fieldNode, boolean optional, ParsingContext context) {
        Operator operator = Operator.NONE;
        Element operatorElement = getOperatorElement(fieldNode);
        if (operatorElement != null) {
            operator = context.getOperatorParser(operatorElement).parse(operatorElement, context);
        }
        Type type = getType(fieldNode, context);
        
        Scalar scalar = new Scalar(getName(fieldNode, context), type, operator, optional);
        if (fieldNode.hasAttribute("id"))
            scalar.setId(fieldNode.getAttribute("id"));
        parseExternalAttributes(fieldNode, scalar);
        return scalar;
    }

    protected Type getType(Element fieldNode, ParsingContext context) {
        return (Type) context.getTypeRegistry().get(getTypeName(fieldNode));
    }

    protected QName getName(Element fieldNode, ParsingContext context) {
        return context.getName();
    }

    protected String getTypeName(Element fieldNode) {
        return fieldNode.getNodeName();
    }

    protected Element getOperatorElement(Element fieldNode) {
        return getElement(fieldNode, 1);
    }
}
