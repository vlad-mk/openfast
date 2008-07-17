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

import java.io.Serializable;
import org.lasalletech.util.Copyable;

public interface Operator extends Copyable<Operator>, Serializable {
    Operator NONE = new AbstractOperator() {
        private static final long serialVersionUID = 1L;
        public String getName() { return "none"; }
        public String getDefaultValue() { return null; }
        public boolean hasDefaultValue() { return false; }
        public Operator copy() {
            return this;
        }
    };
    String getName();
    String getDefaultValue();
    boolean hasDefaultValue();
    Operator copy();
}
