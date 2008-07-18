package org.openfast.simple;

import org.lasalletech.entity.QName;
import org.lasalletech.entity.simple.SimpleField;
import org.openfast.template.Field;
import org.openfast.template.Type;

public class SimpleFastField extends SimpleField<Type> implements Field {
    private static final long serialVersionUID = 1L;

    public SimpleFastField(String name, Type type) {
        super(name, type);
    }

    public void addAttribute(QName name, String value) {}

    public String getAttribute(QName name) {
        return null;
    }

    public String getId() {
        return null;
    }

    public QName getKey() {
        return null;
    }

    public boolean hasAttribute(QName attributeName) {
        return false;
    }

    public boolean isOptional() {
        return !isRequired();
    }

    public void setId(String id) {}

    public void setKey(QName key) {}
}
