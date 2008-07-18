/*
The contents of this file are subject to the Mozilla Public License
Version 1.1 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

The Original Code is OpenFAST.

The Initial Developer of the Original Code is The LaSalle Technology
Group, LLC.  Portions created by The LaSalle Technology Group, LLC
are Copyright (C) The LaSalle Technology Group, LLC. All Rights Reserved.

Contributor(s): Jacob Northey <jacob@lasalletech.com>
                Craig Otis <cotis@lasalletech.com>
 */
package org.openfast.template.loader;

import org.lasalletech.entity.QName;
import org.openfast.MessageTemplateFactory;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.w3c.dom.Element;

public class TemplateParser implements Parser<MessageTemplate> {
    private final boolean loadTemplateIdFromAuxId;
    private final MessageTemplateFactory templateFactory;

    public TemplateParser(MessageTemplateFactory templateFactory, boolean loadTemplateIdFromAuxId) {
        this.templateFactory = templateFactory;
        this.loadTemplateIdFromAuxId = loadTemplateIdFromAuxId;
    }

    /**
     * Creates a MessageTemplate object from the dom template element
     * 
     * @param context
     * @param templateElement
     *            The dom element object
     * @return Returns a newly created MessageTemplate object
     */
    public MessageTemplate parse(Element templateElement, ParsingContext context) {
        Field[] fields = GroupParser.parseFields(templateElement, context);
        MessageTemplate messageTemplate = templateFactory.createMessageTemplate(getTemplateName(templateElement, context), fields);
        GroupParser.parseMore(templateElement, messageTemplate, context);
        if (loadTemplateIdFromAuxId && templateElement.hasAttribute("id")) {
            try {
                int templateId = Integer.parseInt(templateElement.getAttribute("id"));
                context.getTemplateRegistry().register(templateId, messageTemplate);
            } catch (NumberFormatException e) {
                context.getTemplateRegistry().define(messageTemplate);
            }
        } else
            context.getTemplateRegistry().define(messageTemplate);
        return messageTemplate;
    }

    private QName getTemplateName(Element templateElement, ParsingContext context) {
        return new QName(templateElement.getAttribute("name"), context.getTemplateNamespace());
    }

    public boolean canParse(Element element, ParsingContext context) {
        return "template".equals(element.getNodeName());
    }
}
