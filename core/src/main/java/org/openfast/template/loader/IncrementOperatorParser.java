package org.openfast.template.loader;

import org.lasalletech.exom.QName;
import org.openfast.template.Operator;
import org.openfast.template.operator.IncrementOperator;
import org.w3c.dom.Element;

public class IncrementOperatorParser extends DictionaryOperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "increment".equals(element.getNodeName());
    }

    @Override
    public Operator doParse(Element operatorNode, String dictionary, QName key, String defaultValue) {
        return new IncrementOperator(key, dictionary, defaultValue);
    }
}
