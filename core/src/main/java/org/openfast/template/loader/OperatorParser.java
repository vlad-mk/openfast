package org.openfast.template.loader;

import org.openfast.template.Operator;
import org.w3c.dom.Element;

public interface OperatorParser {
    Operator parse(Element operatorNode, ParsingContext context);
    boolean canParse(Element element, ParsingContext context);
}
