package org.openfast.simple;

import org.lasalletech.entity.QName;
import org.openfast.FastObject;
import org.openfast.Global;
import org.openfast.fast.FastTypes;
import org.openfast.template.Operator;
import org.openfast.template.Scalar;
import org.openfast.template.Sequence;

public class SimpleSequence extends SimpleComposite<FastObject> implements Sequence {
    private final Scalar length;

    public SimpleSequence(QName name, Scalar length) {
        super(name);
        this.length = length;
    }

    public FastObject newObject() {
        return null;
    }

    public Scalar getLength() {
        return length;
    }
}
