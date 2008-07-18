package org.openfast.simple;

import org.lasalletech.entity.simple.SimpleEObject;
import org.openfast.FastObject;
import org.openfast.Message;
import org.openfast.template.Field;
import org.openfast.template.Group;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Type;

public class SimpleFastObject extends SimpleEObject<Group, FastObject, Type, Field, MessageTemplate, Message, Group, FastObject> implements FastObject {
    protected SimpleFastObject(Group group) {
        super(group);
    }
}
