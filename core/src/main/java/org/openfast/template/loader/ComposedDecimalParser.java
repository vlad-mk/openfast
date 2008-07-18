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

import org.lasalletech.entity.QName;
import org.openfast.Global;
import org.openfast.template.ComposedScalar;
import org.openfast.template.Field;
import org.openfast.template.Operator;
import org.openfast.template.Scalar;
import org.openfast.template.type.Type;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ComposedDecimalParser extends AbstractFieldParser {
    public ComposedDecimalParser() {
        super("decimal");
    }

    public boolean canParse(Element element, ParsingContext context) {
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            String nodeName = children.item(i).getNodeName();
            if (nodeName.equals("mantissa") || nodeName.equals("exponent"))
                return true;
        }
        return false;
    }

    protected Field parse(Element fieldNode, boolean optional, ParsingContext context) {
        NodeList fieldChildren = fieldNode.getChildNodes();
        Node mantissaNode = null;
        Node exponentNode = null;
        for (int i = 0; i < fieldChildren.getLength(); i++) {
            if ("mantissa".equals(fieldChildren.item(i).getNodeName())) {
                mantissaNode = fieldChildren.item(i);
            } else if ("exponent".equals(fieldChildren.item(i).getNodeName())) {
                exponentNode = fieldChildren.item(i);
            }
        }
        return createComposedDecimal(fieldNode, context.getName(), optional, mantissaNode, exponentNode, context);
    }

    /**
     * Create a new Scalar object with a new TwinValue and a new TwinOperator
     * with the mantissa and exponent nodes. If there are nodes or child nodes
     * within the passed Nodes, those values are stored as well
     * 
     * @param fieldNode
     *            The dom element object
     * @param name
     *            The name of the create Scalar object
     * @param optional
     *            Determines if the Field is required or not for the data
     * @param mantissaNode
     *            The passed mantissaNode
     * @param exponentNode
     *            The passed exponentNode
     * @return Returns a new Scalar object with the newly create TwinValue
     *         object and TwinOperator object.
     */
    private Field createComposedDecimal(Element fieldNode, QName name, boolean optional, Node mantissaNode, Node exponentNode,
            ParsingContext context) {
        Operator mantissaOp = Operator.NONE;
        if ((mantissaNode != null) && mantissaNode.hasChildNodes()) {
            Element operatorElement = getElement((Element) mantissaNode, 1);
            mantissaOp = context.getOperatorParser(operatorElement).parse(operatorElement, context);
        }
        Operator exponentOp = Operator.NONE;
        if ((exponentNode != null) && exponentNode.hasChildNodes()) {
            Element operatorElement = getElement((Element) exponentNode, 1);
            exponentOp = context.getOperatorParser(operatorElement).parse(operatorElement, context);
        }
        Scalar exponentScalar = new Scalar(Global.createImplicitName(name), Type.I32, exponentOp, optional);
        Scalar mantissaScalar = new Scalar(Global.createImplicitName(name), Type.I64, mantissaOp, false);
        ComposedScalar scalar = new ComposedScalar(name, Type.DECIMAL, new Scalar[] { exponentScalar, mantissaScalar }, optional);
        if (fieldNode.hasAttribute("id"))
            scalar.setId(fieldNode.getAttribute("id"));
        return scalar;
    }
}
