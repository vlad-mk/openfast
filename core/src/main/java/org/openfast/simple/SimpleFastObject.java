package org.openfast.simple;

import org.lasalletech.entity.simple.SimpleEObject;
import org.openfast.FastObject;
import org.openfast.Message;
import org.openfast.template.Composite;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Type;

public class SimpleFastObject extends SimpleEObject<Composite<FastObject>, FastObject, Type, Field, MessageTemplate, Message, Composite<FastObject>, FastObject> implements FastObject {
    protected SimpleFastObject(Composite<FastObject> composite) {
        super(composite);
    }
}
