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

import org.lasalletech.entity.QName;
import org.openfast.template.Type;

public class Scalar extends BasicField implements Cloneable {
    private static final long serialVersionUID = 1L;
    private final Operator operator;
    private final Type type;

    /**
     * Scalar constructor - sets the dictionary as global and validates the
     * entries
     * 
     * @param name
     *            The name of Scalar as a string
     * @param type
     *            The type of this Scalar
     * @param operator
     *            Which operator object is being used
     * @param defaultValue
     *            The default value of the ScalarValue
     * @param optional
     *            Determines if the Scalar is required or not for the data
     */
    public Scalar(String name, Type type, Operator operator, boolean optional) {
        this(new QName(name), type, operator, optional);
    }
    
    public Scalar(QName name, Type type, Operator operator, boolean optional) {
        super(name, optional);
        this.operator = operator;
        this.type = type;
    }
    /**
     * Copy constructor
     * 
     * @param scalar
     */
    public Scalar(Scalar scalar) {
        this(new QName(scalar.getQName()), scalar.getType(), (Operator) scalar.getOperator().copy(), scalar.isOptional());
    }
    /**
     * 
     * @return Returns the type as a string
     */
    public Type getType() {
        return type;
    }
    /**
     * 
     * @return Returns the operator name as a string
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * @return Returns the string 'Scalar [name=X, operator=X, dictionary=X]'
     */
    public String toString() {
        return "Scalar [name=" + name.getName() + ", operator=" + operator + ", type=" + type + "]";
    }
    /**
     * @return Returns the string 'scalar'
     */
    public String getTypeName() {
        return "scalar";
    }
    public Class<?> getValueType() {
        return null;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
