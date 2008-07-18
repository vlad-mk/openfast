package org.openfast.simple;

import org.lasalletech.entity.EObject;
import org.lasalletech.entity.Entity;
import org.lasalletech.entity.EntityType;
import org.lasalletech.entity.QName;
import org.openfast.template.Type;

@SuppressWarnings("unchecked")
public class FastEntityType<T extends Entity> extends EntityType<T> implements Type {
    private static final long serialVersionUID = 1L;
    
    public FastEntityType(T entity) {
        this(entity, false);
    }
    
    public FastEntityType(T entity, boolean repeating) {
        super(entity, repeating);
    }


    public void parse(EObject o, int index, String value) {
        throw new UnsupportedOperationException();
    }
}
