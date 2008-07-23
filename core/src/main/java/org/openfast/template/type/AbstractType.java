/*
The contents of this file are subject to the Mozilla Public License
Version 1.1 (the "License"); you may not use this file except in
compliance with the License. You may obtain a copy of the License at
http://www.mozilla.org/MPL/

Software distributed under the License is distributed on an "AS IS"
basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
License for the specific language governing rights and limitations
under the License.

The Original Code is OpenFAST.

The Initial Developer of the Original Code is The LaSalle Technology
Group, LLC.  Portions created by The LaSalle Technology Group, LLC
are Copyright (C) The LaSalle Technology Group, LLC. All Rights Reserved.

Contributor(s): Jacob Northey <jacob@lasalletech.com>
                Craig Otis <cotis@lasalletech.com>
 */
package org.openfast.template.type;

import java.io.Serializable;
import org.lasalletech.entity.QName;
import org.openfast.template.Type;

public abstract class AbstractType implements Type, Serializable {
    private static final long serialVersionUID = 1L;
    private final QName name;

    public AbstractType(String typeName) {
        this.name = new QName(typeName);
    }
    
    public boolean isRepeating() {
        return false;
    }
    
    public boolean isPrimitive() {
        return true;
    }

    /**
     * 
     * @return Returns name as a string
     */
    public String getName() {
        return name.getName();
    }
    /**
     * @return Returns the name as a string
     */
    public String toString() {
        return name.getName();
    }
    
    public QName getQName() {
        return name;
    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || !obj.getClass().equals(this.getClass()))
            return false;
        return name.equals(((Type) obj).getName());
    }
    public int hashCode() {
        return name.hashCode();
    }
}
