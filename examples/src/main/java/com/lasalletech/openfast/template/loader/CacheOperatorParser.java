package com.lasalletech.openfast.template.loader;

import org.lasalletech.entity.QName;
import org.openfast.template.Operator;
import org.openfast.template.loader.OperatorParser;
import org.openfast.template.loader.ParsingContext;
import org.w3c.dom.Element;
import com.lasalletech.openfast.template.operator.CacheOperator;

public class CacheOperatorParser implements OperatorParser {
    public boolean canParse(Element element, ParsingContext context) {
        return "cache".equals(element.getNodeName());
    }

    public Operator parse(Element operatorNode, ParsingContext context) {
        String ns = context.getNamespace();
        int size = Integer.parseInt(operatorNode.getAttribute("size"));
        return new CacheOperator(new QName(operatorNode.getAttribute("name"), ns), size);
    }
}
