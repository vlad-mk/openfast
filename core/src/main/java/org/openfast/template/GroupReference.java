package org.openfast.template;

import org.lasalletech.exom.EObject;
import org.lasalletech.exom.EntityType;
import org.lasalletech.exom.QName;

public class GroupReference extends EntityType {

    public GroupReference(Group group) {
        super(group);
    }

    public boolean isRepeating() {
        return false;
    }

    public void parse(EObject object, int index, char[] data, int offset, int length) {}

    public void parse(EObject object, String fieldName, char[] data, int offset, int length) {}

    public void parse(EObject object, int index, byte[] data, int offset, int length) {}

    public void parse(EObject object, String fieldName, byte[] data, int offset, int length) {}

    public byte[] serialize(EObject message, int index) {
        return null;
    }

    public QName getQName() {
        return null;
    }
}
