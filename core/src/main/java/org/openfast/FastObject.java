package org.openfast;

import org.lasalletech.entity.EObject;
import org.openfast.template.Composite;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Type;

public interface FastObject extends EObject<Composite<FastObject>, FastObject, Type, Field, MessageTemplate, Message, Composite<FastObject>, FastObject> {
    
}
