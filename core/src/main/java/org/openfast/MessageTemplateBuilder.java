package org.openfast;

import java.util.ArrayList;
import java.util.List;
import org.lasalletech.entity.QName;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;

public class MessageTemplateBuilder {
    private final QName name;
    private final MessageTemplateFactory factory;
    private final List<Field> fields = new ArrayList<Field>();

    public MessageTemplateBuilder(MessageTemplateFactory factory, String name) {
        this.name = new QName(name);
        this.factory = factory;
    }

    public MessageTemplateBuilder(MessageTemplateFactory factory, QName name) {
        this.factory = factory;
        this.name = name;
    }
   
    public MessageTemplate build() {
        return factory.createMessageTemplate(name, fields.toArray(new Field[fields.size()]));
    }
}
