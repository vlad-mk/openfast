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
package org.openfast.template;

import org.lasalletech.exom.QName;

public class DynamicTemplateReference extends Field {
    private static final long serialVersionUID = 1L;
    public static final DynamicTemplateReference INSTANCE = new DynamicTemplateReference();

    private DynamicTemplateReference() {
        super(QName.NULL, false);
    }

    /**
     * @return Returns null
     */
    public String getTypeName() {
        return null;
    }

    /**
     * @return Returns null
     */
    public Class getValueType() {
        return null;
    }

    /**
     * @return Returns false
     */
    public boolean usesPresenceMapBit() {
        return false;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass().equals(this.getClass());
    }
}
