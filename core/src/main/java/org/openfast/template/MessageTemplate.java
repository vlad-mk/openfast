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
import org.openfast.Message;
import org.openfast.template.type.Type;

public class MessageTemplate extends Group {
    private static final long serialVersionUID = 1L;

    public MessageTemplate(QName name, Field[] fields) {
        super(name, addTemplateIdField(fields), false);
    }

    public boolean usesPresenceMap() {
        return true;
    }

    public MessageTemplate(String name, Field[] fields) {
        this(new QName(name), fields);
    }

    /**
     * Take an existing field array and add TemplateID information to it
     * 
     * @param fields
     *            The field array that needs the TemplateID added to it
     * @return Returns a new array with the passed field information and
     *         TemplateID
     */
    private static Field[] addTemplateIdField(Field[] fields) {
        Field[] newFields = new Field[fields.length + 1];
        newFields[0] = new Scalar("templateId", Type.U32, null, false);
        System.arraycopy(fields, 0, newFields, 1, fields.length);
        return newFields;
    }

    /**
     * @return Returns the length of the fields as an int
     */
    public int getFieldCount() {
        return fields.length;
    }

    /**
     * @return Returns the class of the message
     */
    public Class getValueType() {
        return Message.class;
    }

    public String toString() {
        return getName();
    }

    /**
     * Returns a field array of the current stored fields
     * 
     * @return Returns a field array
     */
    public Field[] getTemplateFields() {
        Field[] f = new Field[fields.length - 1];
        System.arraycopy(fields, 1, f, 0, fields.length - 1);
        return f;
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof MessageTemplate))
            return false;
        return equals((MessageTemplate) obj);
    }

    private boolean equals(MessageTemplate other) {
        if (fields.length != other.fields.length)
            return false;
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].equals(other.fields[i]))
                return false;
        }
        return true;
    }
}
