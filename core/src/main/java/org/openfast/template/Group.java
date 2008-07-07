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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.lasalletech.exom.QName;
import org.lasalletech.exom.Type;
import org.lasalletech.exom.simple.SimpleEntity;

public class Group extends SimpleEntity implements Field {
    private static final long serialVersionUID = 1L;
    protected QName typeReference = null;
    protected String childNamespace = "";
    protected final boolean optional;
    protected final Field field;
    protected List<StaticTemplateReference> staticTemplateReferences = Collections.emptyList();

    public Group(String name, Field[] fields, boolean optional) {
        this(new QName(name), fields, optional);
    }

    public Group(QName name, Field[] fields, boolean optional) {
        super(name.getName());
        field = new BasicField(name, optional);
        this.optional = optional;
        for (Field field : fields) {
            if (field instanceof StaticTemplateReference) {
              StaticTemplateReference reference = (StaticTemplateReference) field;
              for (org.lasalletech.exom.Field referencedField : reference.getTemplate().getFields())
                  add(referencedField);
              if (staticTemplateReferences.isEmpty()) {
                  staticTemplateReferences = new LinkedList<StaticTemplateReference>();
              }
              staticTemplateReferences.add(reference);
            } else {
                add(field);
            }
        }
    }

    /**
     * @return Returns the string 'group'
     */
    public String getTypeName() {
        return "group";
    }

    /**
     * Get the Sequence of the passed fieldName
     * 
     * @param fieldName
     *            The field name that is being searched for
     * @return Returns a sequence object of the specified fieldName
     */
    public Sequence getSequence(String fieldName) {
        return (Sequence) getField(fieldName);
    }

    /**
     * Get the Scalar Value of the passed fieldName
     * 
     * @param fieldName
     *            The field name that is being searched for
     * @return Returns a Scalar value of the specified fieldName
     */
    public Scalar getScalar(String fieldName) {
        return (Scalar) getField(fieldName);
    }

    public Scalar getScalar(int index) {
        return (Scalar) getField(index);
    }

    /**
     * Find the group with the passed fieldName
     * 
     * @param fieldName
     *            The field name that is being searched for
     * @return Returns a Group object of the specified field name
     */
    public Group getGroup(String fieldName) {
        return (Group) ((GroupReference) getField(fieldName).getType()).getEntity();
    }

    /**
     * Set the name of the type referenced by this group
     * 
     * @param typeReference
     *            The name of the application type referenced by this goup
     */
    public void setTypeReference(QName typeReference) {
        this.typeReference = typeReference;
    }

    /**
     * 
     * @return Returns the application type referenced by this group
     */
    public QName getTypeReference() {
        return typeReference;
    }

    /**
     * @return Returns true if the type has a reference, false otherwise
     */
    public boolean hasTypeReference() {
        return typeReference != null;
    }

    public String toString() {
        return getName();
    }

    public String getChildNamespace() {
        return childNamespace;
    }

    public void setChildNamespace(String childNamespace) {
        this.childNamespace = childNamespace;
    }

    public Type getType() {
        return null;
    }

    public boolean isRequired() {
        return !optional;
    }

    public void addAttribute(QName name, String value) {
        field.addAttribute(name, value);
    }

    public String getAttribute(QName name) {
        return field.getAttribute(name);
    }

    public String getId() {
        return field.getId();
    }

    public QName getKey() {
        return field.getKey();
    }

    public boolean hasAttribute(QName attributeName) {
        return field.hasAttribute(attributeName);
    }

    public boolean isOptional() {
        return false;
    }

    public void setId(String id) {
        field.setId(id);
    }

    public void setKey(QName key) {
        field.setKey(key);
    }
}