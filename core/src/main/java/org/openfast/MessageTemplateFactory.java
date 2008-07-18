package org.openfast;

import org.lasalletech.entity.QName;
import org.openfast.template.Field;
import org.openfast.template.Group;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.Sequence;

public interface MessageTemplateFactory {
    MessageTemplate createMessageTemplate(String name, Field[] fields);
    MessageTemplate createMessageTemplate(QName name, Field[] fields);
    MessageTemplateBuilder createMessageTemplateBuilder(String name);
    MessageTemplateBuilder createMessageTemplateBuilder(QName name);
    Field createField(Group group, boolean optional);
    Field createField(Sequence sequence, boolean optional);
    Group createGroup(QName name, Field[] parseFields);
    Sequence createSequence(QName name, Scalar parseSequenceLengthField, Field[] parseFields);
}
