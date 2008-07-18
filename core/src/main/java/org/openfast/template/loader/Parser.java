package org.openfast.template.loader;

import org.w3c.dom.Element;

public interface Parser<T> {
    T parse(Element fieldNode, ParsingContext context);

    boolean canParse(Element element, ParsingContext context);
}
