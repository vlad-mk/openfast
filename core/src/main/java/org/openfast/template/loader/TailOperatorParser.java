package org.openfast.template.loader;

import org.lasalletech.entity.QName;
import org.openfast.template.Operator;
import org.openfast.template.operator.TailOperator;
import org.w3c.dom.Element;

public class TailOperatorParser extends DictionaryOperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "tail".equals(element.getNodeName());
    }

    @Override
    public Operator doParse(Element operatorNode, String dictionary, QName key, String defaultValue) {
        return new TailOperator(key, dictionary, defaultValue);
    }
}
