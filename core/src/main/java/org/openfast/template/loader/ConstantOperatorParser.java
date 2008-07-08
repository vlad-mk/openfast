package org.openfast.template.loader;

import org.openfast.template.Operator;
import org.openfast.template.operator.ConstantOperator;
import org.w3c.dom.Element;

public class ConstantOperatorParser implements OperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "constant".equals(element.getNodeName());
    }

    public Operator parse(Element operatorNode, ParsingContext context) {
        String defaultValue = null;
        if (operatorNode.hasAttribute("value"))
            defaultValue = operatorNode.getAttribute("value");
        return new ConstantOperator(defaultValue);
    }
}
