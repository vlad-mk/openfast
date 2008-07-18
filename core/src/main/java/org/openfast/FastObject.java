package org.openfast;

import org.lasalletech.entity.EObject;
import org.openfast.template.Field;
import org.openfast.template.Group;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Type;

public interface FastObject extends EObject<Group, FastObject, Type, Field, MessageTemplate, Message, Group, FastObject> {
    
}
