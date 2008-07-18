package org.openfast.template.loader;

import org.lasalletech.entity.QName;
import org.openfast.template.Operator;
import org.w3c.dom.Element;

public abstract class DictionaryOperatorParser implements OperatorParser {
    public Operator parse(Element operatorElement, ParsingContext context) {
        String defaultValue = null;
        String key = context.getName().getName();
        String ns = context.getNamespace();
        String dictionary = operatorElement.hasAttribute("dictionary") ? operatorElement.getAttribute("dictionary") : context.getDictionary();
        if (operatorElement.hasAttribute("value"))
            defaultValue = operatorElement.getAttribute("value");
        if (operatorElement.hasAttribute("key"))
            key = operatorElement.getAttribute("key");
        if (operatorElement.hasAttribute("ns"))
            ns = operatorElement.getAttribute("ns");
        return doParse(operatorElement, dictionary, new QName(key, ns), defaultValue);
    }
    
    public abstract Operator doParse(Element operatorNode, String dictionary, QName key, String defaultValue);
}
