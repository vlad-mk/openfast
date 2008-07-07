package org.openfast.template.loader;

import org.lasalletech.exom.QName;
import org.openfast.template.CopyOperator;
import org.openfast.template.Operator;
import org.w3c.dom.Element;

public class CopyOperatorParser extends DictionaryOperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "copy".equals(element.getNodeName());
    }

    @Override
    public Operator doParse(Element operatorNode, String dictionary, QName key, String defaultValue) {
        return new CopyOperator(key, dictionary, defaultValue);
    }
}
