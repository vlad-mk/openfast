package org.openfast.template.loader;

import org.lasalletech.exom.QName;
import org.openfast.template.DeltaOperator;
import org.openfast.template.Operator;
import org.w3c.dom.Element;

public class DeltaOperatorParser extends DictionaryOperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "delta".equals(element.getNodeName());
    }

    @Override
    public Operator doParse(Element operatorNode, String dictionary, QName key, String defaultValue) {
        return new DeltaOperator(key, dictionary, defaultValue);
    }
}
