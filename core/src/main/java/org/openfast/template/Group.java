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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lasalletech.exom.QName;
import org.lasalletech.exom.Type;
import org.lasalletech.exom.simple.SimpleEntity;
import org.openfast.error.FastConstants;

public class Group extends SimpleEntity implements org.lasalletech.exom.Field {
    private static final long serialVersionUID = 1L;
    private QName typeReference = null;
    protected String childNamespace = "";
    protected final Field[] fields;
    protected final Map fieldIndexMap;
    protected final Map fieldIdMap;
    protected final Map fieldNameMap;
    protected final boolean usesPresenceMap;
    protected final StaticTemplateReference[] staticTemplateReferences;
    protected final Field[] fieldDefinitions;
    protected final Map introspectiveFieldMap;
    protected final boolean optional;

    public Group(String name, Field[] fields, boolean optional) {
        this(new QName(name), fields, optional);
    }

    public Group(QName name, Field[] fields, boolean optional) {
        super(name.getName());
        this.optional = optional;
        List expandedFields = new ArrayList();
        List staticTemplateReferences = new ArrayList();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof StaticTemplateReference) {
                Field[] referenceFields = null;// ((StaticTemplateReference) fields[i]).getTemplate().getFields();
                for (int j = 1; j < referenceFields.length; j++)
                    expandedFields.add(referenceFields[j]);
                staticTemplateReferences.add(fields[i]);
            } else {
                expandedFields.add(fields[i]);
            }
        }
        this.fields = (Field[]) expandedFields.toArray(new Field[expandedFields.size()]);
        this.fieldDefinitions = fields;
        this.fieldIndexMap = constructFieldIndexMap(this.fields);
        this.fieldNameMap = constructFieldNameMap(this.fields);
        this.fieldIdMap = constructFieldIdMap(this.fields);
        this.introspectiveFieldMap = constructInstrospectiveFields(this.fields);
        this.usesPresenceMap = determinePresenceMapUsage(this.fields);
        this.staticTemplateReferences = (StaticTemplateReference[]) staticTemplateReferences
                .toArray(new StaticTemplateReference[staticTemplateReferences.size()]);
    }

    // BAD ABSTRACTION
    private static Map constructInstrospectiveFields(Field[] fields) {
        Map map = new HashMap();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof Scalar) {
                if (fields[i].hasAttribute(FastConstants.LENGTH_FIELD)) {
                    map.put(fields[i].getAttribute(FastConstants.LENGTH_FIELD), fields[i]);
                }
            }
        }
        if (map.size() == 0)
            return Collections.EMPTY_MAP;
        return map;
    }

    /**
     * Check to see if the passed field array has a Field that has a MapBit
     * present
     * 
     * @param fields
     *            The Field object array to be checked
     * @return Returns true if a Field object has a MapBit present, false
     *         otherwise
     */
    private static boolean determinePresenceMapUsage(Field[] fields) {
        for (int i = 0; i < fields.length; i++)
            if (fields[i].usesPresenceMapBit())
                return true;
        return false;
    }

    private int getMaxPresenceMapSize() {
        return fields.length * 2;
    }

    /**
     * @return Returns the optional boolean of the MapBit
     */
    public boolean usesPresenceMapBit() {
        return optional;
    }

    public boolean usesPresenceMap() {
        return usesPresenceMap;
    }

    /**
     * Find the number of total fields
     * 
     * @return Returns the number of fields
     */
    public int getFieldCount() {
        return fields.length;
    }

    /**
     * @return Returns the string 'group'
     */
    public String getTypeName() {
        return "group";
    }

    /**
     * Creates a map of the passed field array by the field name and the field
     * index number
     * 
     * @param fields
     *            The name of the field array that is going to be placed into a
     *            new map object
     * @return Returns a map object of the field array passed to it
     */
    private static Map constructFieldNameMap(Field[] fields) {
        Map map = new HashMap();
        for (int i = 0; i < fields.length; i++)
            map.put(fields[i].getQName(), fields[i]);
        return map;
    }

    private static Map constructFieldIdMap(Field[] fields) {
        Map map = new HashMap();
        for (int i = 0; i < fields.length; i++)
            map.put(fields[i].getId(), fields[i]);
        return map;
    }

    /**
     * Creates a map of the passed field array by the field index number,
     * numbered 0 to n
     * 
     * @param fields
     *            The name of the field array that is going to be placed into a
     *            new map object
     * @return Returns a map object of the field array passed to it
     */
    private static Map constructFieldIndexMap(Field[] fields) {
        Map map = new HashMap();
        for (int i = 0; i < fields.length; i++)
            map.put(fields[i], new Integer(i));
        return map;
    }

    /**
     * Find the index of the passed field name as an integer
     * 
     * @param fieldName
     *            The field name that is being searched for
     * @return Returns an integer of the field index of the specified field name
     */
    public int getFieldIndex(String fieldName) {
        return ((Integer) fieldIndexMap.get(getField(fieldName))).intValue();
    }

    public int getFieldIndex(Field field) {
        return ((Integer) fieldIndexMap.get(field)).intValue();
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
     * Determine if the map has a specified field name.
     * 
     * @param fieldName
     *            The name of the fieldName that is being searched for
     * @return Returns true if there is the field name that was passed in the
     *         Map, false otherwise
     */
    public boolean hasField(String fieldName) {
        return fieldNameMap.containsKey(new QName(fieldName, childNamespace));
    }

    public boolean hasField(QName fieldName) {
        return fieldNameMap.containsKey(fieldName);
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

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Group.hashCode(fields);
        result = prime * result + ((typeReference == null) ? 0 : typeReference.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        final Group other = (Group) obj;
        if (other.fields.length != fields.length)
            return false;
        for (int i = 0; i < fields.length; i++)
            if (!fields[i].equals(other.fields[i]))
                return false;
        return true;
    }

    private static int hashCode(Object[] array) {
        final int prime = 31;
        if (array == null)
            return 0;
        int result = 1;
        for (int index = 0; index < array.length; index++) {
            result = prime * result + (array[index] == null ? 0 : array[index].hashCode());
        }
        return result;
    }

    public boolean hasFieldWithId(String id) {
        return fieldIdMap.containsKey(id);
    }

    public Field getFieldById(String id) {
        return (Field) fieldIdMap.get(id);
    }

    public String getChildNamespace() {
        return childNamespace;
    }

    public void setChildNamespace(String childNamespace) {
        this.childNamespace = childNamespace;
    }

    public StaticTemplateReference[] getStaticTemplateReferences() {
        return staticTemplateReferences;
    }

    public StaticTemplateReference getStaticTemplateReference(String name) {
        return getStaticTemplateReference(new QName(name, ""));
    }

    public StaticTemplateReference getStaticTemplateReference(QName name) {
        for (int i = 0; i < staticTemplateReferences.length; i++) {
            if (staticTemplateReferences[i].getQName().equals(name))
                return staticTemplateReferences[i];
        }
        return null;
    }

    public Field[] getFieldDefinitions() {
        return fieldDefinitions;
    }

    public boolean hasIntrospectiveField(String fieldName) {
        return introspectiveFieldMap.containsKey(fieldName);
    }

    public Scalar getIntrospectiveField(String fieldName) {
        return (Scalar) introspectiveFieldMap.get(fieldName);
    }

    public Type getType() {
        return null;
    }

    public String getName() {
        return null;
    }
}
