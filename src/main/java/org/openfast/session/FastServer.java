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

import org.openfast.error.ErrorHandler;

public class FastServer implements ConnectionListener {
    private ErrorHandler errorHandler = ErrorHandler.DEFAULT;
    private SessionHandler sessionHandler = SessionHandler.NULL;
    private boolean listening;
    private final SessionProtocol sessionProtocol;
    private final Endpoint endpoint;
    private final String serverName;
    private Thread serverThread;

    public FastServer(String serverName, SessionProtocol sessionProtocol, Endpoint endpoint) {
        if (endpoint == null || sessionProtocol == null) {
            throw new NullPointerException();
        }
        this.endpoint = endpoint;
        this.sessionProtocol = sessionProtocol;
        this.serverName = serverName;
        endpoint.setConnectionListener(this);
    }
    public void listen() {
        listening = true;
        if (serverThread == null) {
            Runnable runnable = new Runnable() {
                public void run() {
                    while (listening) {
                        try {
                            endpoint.accept();
                        } catch (Exception e) {
                            errorHandler.error(null, null, e);
                        }
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {}
                    }
                }
            };
            serverThread = new Thread(runnable, "FastServer");
        }
        serverThread.start();
    }
    public void close() throws FastConnectionException {
        listening = false;
        endpoint.close();
    }
    // ************* OPTIONAL DEPENDENCY SETTERS **************
    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler == null) {
            throw new NullPointerException();
        }
        this.errorHandler = errorHandler;
    }
    public void onConnect(Connection connection) {
        Session session = sessionProtocol.onNewConnection(serverName, connection);
        this.sessionHandler.newSession(session);
    }
    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }
}
