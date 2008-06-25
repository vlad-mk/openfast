package org.openfast;

import org.lasalletech.exom.EObject;
import org.lasalletech.exom.Entity;

public interface FastDictionary {
    int lookupInt(Entity template, QName key, QName currentApplicationType);
    
    void store(Entity template, QName key, QName currentApplicationType, int value);
    void storeNull(Entity entity, QName key, QName currentApplicationType);
    
    boolean isDefined(EObject object, QName key, QName currentApplicationType);
    boolean isNull(EObject object, QName key, QName currentApplicationType);
    
    void reset();
}
