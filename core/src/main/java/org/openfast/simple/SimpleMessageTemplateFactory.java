package org.openfast.simple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.lasalletech.entity.QName;
import org.openfast.MessageTemplateBuilder;
import org.openfast.MessageTemplateFactory;
import org.openfast.template.Field;
import org.openfast.template.Group;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.Sequence;
import org.openfast.template.StaticTemplateReference;

public class SimpleMessageTemplateFactory implements MessageTemplateFactory {
    public MessageTemplate createMessageTemplate(String name, Field[] fields) {
        SimpleMessageTemplate template = new SimpleMessageTemplate(name);
        for (Field field : getFields(fields))
            template.add(field);
        return template;
    }

    public MessageTemplate createMessageTemplate(QName name, Field[] fields) {
        SimpleMessageTemplate template = new SimpleMessageTemplate(name);
        for (Field field : getFields(fields))
            template.add(field);
        return template;
    }

    public MessageTemplateBuilder createMessageTemplateBuilder(String name) {
        return new MessageTemplateBuilder(this, name);
    }

    public MessageTemplateBuilder createMessageTemplateBuilder(QName name) {
        return new MessageTemplateBuilder(this, name);
    }

    public Field createField(Group group, boolean optional) {
        return new SimpleFastField(group.getName(), new FastEntityType<Group>(group));
    }

    public Field createField(Sequence sequence, boolean optional) {
        return new SimpleFastField(sequence.getName(), new FastEntityType<Sequence>(sequence, true));
    }

    public Group createGroup(QName name, Field[] fields) {
        SimpleGroup group = new SimpleGroup(name);
        for (Field field : getFields(fields))
            group.add(field);
        return group;
    }

    public Sequence createSequence(QName name, Scalar length, Field[] fields) {
        SimpleSequence sequence = new SimpleSequence(name, length);
        for (Field field : getFields(fields))
            sequence.add(field);
        return sequence;
    }

    private List<Field> getFields(Field[] fields) {
        List<Field> newFields = new ArrayList<Field>(); 
        for (Field field : fields) {
            if (field instanceof StaticTemplateReference) {
                StaticTemplateReference reference = (StaticTemplateReference) field;
                for (Field referencedField : reference.getTemplate().getFields())
                    newFields.add(referencedField);
//                if (staticTemplateReferences.isEmpty()) {
//                    staticTemplateReferences = new LinkedList<StaticTemplateReference>();
//                }
//                staticTemplateReferences.add(reference);
            } else {
                newFields.add(field);
            }
        }
        return newFields;
    }
}
