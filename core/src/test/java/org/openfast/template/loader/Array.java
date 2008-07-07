package org.openfast.template.loader;

import org.lasalletech.exom.QName;
import org.openfast.template.BasicField;

public class Array extends BasicField {
    public Array(QName name, boolean optional) {
        super(name, optional);
    }

    private static final long serialVersionUID = 1L;

    public String getTypeName() {
        return null;
    }

    public Class getValueType() {
        return null;
    }

    public boolean usesPresenceMapBit() {
        return false;
    }

    public boolean equals(Object obj) {
        return name.equals(((Array) obj).name);
    }
}
