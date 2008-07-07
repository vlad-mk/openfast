package org.openfast.template.loader;

import org.openfast.template.DefaultOperator;
import org.openfast.template.Operator;
import org.w3c.dom.Element;

public class DefaultOperatorParser implements OperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "default".equals(element.getNodeName());
    }

    public Operator parse(Element operatorElement, ParsingContext context) {
        String defaultValue = null;
        if (operatorElement.hasAttribute("value"))
            defaultValue = operatorElement.getAttribute("value");
        return new DefaultOperator(defaultValue);
    }
}
