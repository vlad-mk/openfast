package org.openfast.simple;

import org.lasalletech.entity.QName;
import org.openfast.Message;
import org.openfast.template.MessageTemplate;

public class SimpleMessageTemplate extends SimpleComposite<Message> implements MessageTemplate {
    public SimpleMessageTemplate(QName name) {
        super(name);
    }
    
    public SimpleMessageTemplate(String name) {
        super(new QName(name));
    }

    public Message newObject() {
        return new SimpleMessage(this);
    }
}
