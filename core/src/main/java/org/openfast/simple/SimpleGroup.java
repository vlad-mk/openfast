package org.openfast.simple;

import org.lasalletech.entity.QName;
import org.openfast.FastObject;
import org.openfast.template.Group;

public class SimpleGroup extends SimpleComposite<FastObject> implements Group {

    public SimpleGroup(QName name) {
        super(name);
    }

    public FastObject newObject() {
        return new SimpleFastObject(this);
    }
}
