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

import java.util.HashMap;
import java.util.Map;
import org.lasalletech.exom.QName;
import org.lasalletech.exom.Type;

public class BasicField implements Field {
    private static final long serialVersionUID = 1L;
    protected final QName name;
    protected QName key;
    protected final boolean optional;
    protected String id;
    private Map<QName, String> attributes;

    /**
     * Field Constructor
     * 
     * @param name
     *            The name of the Field, a string
     * @param optional
     *            Determines if the Field is required or not for the data
     */
    public BasicField(QName name, boolean optional) {
        this.name = name;
        this.key = name;
        this.optional = optional;
    }

    /**
     * Field Constructor
     * 
     * @param name
     *            The name of the Field, a string
     * @param key
     *            The key of the Field, a string
     * @param optional
     *            Determines if the Field is required or not for the data
     */
    public BasicField(QName name, QName key, boolean optional) {
        this.name = name;
        this.key = key;
        this.optional = optional;
    }

    /**
     * Field Constructor
     * 
     * @param name
     *            The name of the Field, a string
     * @param key
     *            The key of the Field, a string
     * @param optional
     *            Determines if the Field is required or not for the data
     * @param id
     *            The id string
     */
    public BasicField(String name, String key, boolean optional, String id) {
        this.name = new QName(name);
        this.key = new QName(key);
        this.optional = optional;
        this.id = id;
    }

    /**
     * Find the name
     * 
     * @return Returns the name of the Field as a string
     */
    public String getName() {
        return name.getName();
    }

    public QName getQName() {
        return name;
    }

    /**
     * Check to see if the Field is required
     * 
     * @return Returns true if the Field isn't required, false otherwise
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Find the key
     * 
     * @return Returns the Key as a string
     */
    public QName getKey() {
        return key;
    }

    /**
     * Sets the passed key to the current field key
     * 
     * @param key
     *            The key to be set
     */
    public void setKey(QName key) {
        this.key = key;
    }

    /**
     * Find the ID
     * 
     * @return Returns the ID as a string
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID
     * 
     * @param id
     *            The new ID to set the Field's ID to
     */
    public void setId(String id) {
        this.id = id;
    }

    public boolean hasAttribute(QName attributeName) {
        return attributes != null && attributes.containsKey(attributeName);
    }

    public void addAttribute(QName name, String value) {
        if (attributes == null)
            attributes = new HashMap<QName, String>(4);
        attributes.put(name, value);
    }

    public String getAttribute(QName name) {
        return (String) attributes.get(name);
    }

    public Type getType() {
        throw new UnsupportedOperationException();
    }

    public boolean isRequired() {
        return !optional;
    }
}
