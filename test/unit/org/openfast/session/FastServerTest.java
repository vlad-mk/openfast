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


package org.openfast.session;

import junit.framework.TestCase;


public class FastServerTest extends TestCase {
    private FastServer server = new FastServer("test", SessionFactory.NULL);

    public void testConstructWithNullFactory() {
        try {
            new FastServer("name", null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testConstructWithNullOrEmptyName() {
        try {
            new FastServer(null, SessionFactory.NULL);
            fail();
        } catch (NullPointerException e) {
        }

        try {
            new FastServer(" ", SessionFactory.NULL);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testSetConnectionListenerNull() {
        try {
            server.setConnectionListener(null);
            fail();
        } catch (NullPointerException e) {
        }
    }

    public void testSetErrorHandlerNull() {
        try {
            server.setErrorHandler(null);
            fail();
        } catch (NullPointerException e) {
        }
    }
}
