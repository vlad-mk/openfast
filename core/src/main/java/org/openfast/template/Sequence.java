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
import org.openfast.Global;
import org.openfast.template.type.Type;

public class Sequence extends BasicField {
    private static final long serialVersionUID = 1L;
    private final Group group;
    private final Scalar length;
    private boolean implicitLength;

    /**
     * Sequence Constructor - Sets the implicitLength to true
     * 
     * @param name
     *            The name of the sequence as a string
     * @param fields
     *            Field array object
     * @param optional
     *            Determines if the Field is required or not for the data
     * 
     */
    public Sequence(QName name, Field[] fields, boolean optional) {
        this(name, createLength(name, optional), fields, optional);
        implicitLength = true;
    }

    public Sequence(String name, Field[] fields, boolean optional) {
        this(new QName(name), fields, optional);
    }

    /**
     * Sequence Constructor - If no length, a length is created and the
     * implicitLength is set to true. A new Group is also created with with the
     * respected information.
     * 
     * @param name
     *            Name of the sequence, a string
     * @param length
     *            Length of the sequence, a Scalar value
     * @param fields
     *            Field array
     * @param optional
     *            Determines if the Field is required or not for the data
     */
    public Sequence(QName name, Scalar length, Field[] fields, boolean optional) {
        super(name, optional);
        this.group = new Group(name, fields, optional);
        if (length == null) {
            this.length = createLength(name, optional);
            implicitLength = true;
        } else {
            this.length = length;
        }
    }

    /**
     * Creates a Scalar value length
     * 
     * @param name
     *            The name of the Scalar object
     * @param optional
     *            Determines if the Field is required or not for the data
     * @return A Scalar value
     */
    private static Scalar createLength(QName name, boolean optional) {
        return new Scalar(Global.createImplicitName(name), Type.U32, null, optional);
    }

    /**
     * Find the number of fields in the current group
     * 
     * @return Returns an integer of the number of fields
     */
    public int getFieldCount() {
        return group.getFieldCount();
    }

    /**
     * Find the length of a Scalar value
     * 
     * @return The length of the Scalar value
     */
    public Scalar getLength() {
        return length;
    }

    /**
     * @return Returns the string 'sequence'
     */
    public String getTypeName() {
        return "sequence";
    }

    /**
     * 
     * @return Return the current Group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * 
     * @param fieldName
     *            String of the FieldName that is to be found
     * @return Returns true if there is a field with the specified name, false
     *         otherwise
     */
    public boolean hasField(String fieldName) {
        return group.hasField(fieldName);
    }

    /**
     * 
     * @return Returns the implicitLength, true or false - whichever is set
     */
    public boolean isImplicitLength() {
        return implicitLength;
    }

    /**
     * Set the type reference
     * 
     * @param typeReference
     *            The type reference name as a string
     */
    public void setTypeReference(QName typeReference) {
        this.group.setTypeReference(typeReference);
    }

    /**
     * 
     * @return Returns the typeReference as a string
     */
    public QName getTypeReference() {
        return group.getTypeReference();
    }

    /**
     * 
     * @return Returns true if there is a type reference
     */
    public boolean hasTypeReference() {
        return group.hasTypeReference();
    }

    public String toString() {
        return name.getName();
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((group == null) ? 0 : group.hashCode());
        result = prime * result + ((length == null) ? 0 : length.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final Sequence other = (Sequence) obj;
        if (!group.equals(other.group))
            return false;
        if (isImplicitLength() != other.isImplicitLength())
            return false;
        if (!isImplicitLength() && !length.equals(other.length))
            return false;
        return true;
    }

    public Class getValueType() {
        return null;
    }
}
