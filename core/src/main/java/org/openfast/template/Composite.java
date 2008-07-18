package org.openfast.template;

import org.lasalletech.entity.EObject;
import org.lasalletech.entity.Entity;
import org.lasalletech.entity.QName;

@SuppressWarnings("unchecked")
public interface Composite<O extends EObject> extends Entity<O, Field> {
    Scalar getScalar(int index);
    Sequence getSequence(int index);
    Group getGroup(int index);
    void setChildNamespace(String namespace);
    void setTypeReference(QName typeReference);
    boolean hasTypeReference();
    QName getTypeReference();
}
