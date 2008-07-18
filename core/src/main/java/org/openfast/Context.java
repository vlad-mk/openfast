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
package org.openfast;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lasalletech.entity.QName;
import org.openfast.dictionary.FastDictionary;
import org.openfast.dictionary.GlobalFastDictionary;
import org.openfast.error.ErrorHandler;
import org.openfast.error.FastConstants;
import org.openfast.template.BasicTemplateRegistry;
import org.openfast.template.MessageTemplate;
import org.openfast.template.TemplateRegisteredListener;
import org.openfast.template.TemplateRegistry;

/**
 * Manages current state of an encoding or decoding process.  Each encoder/decoder should have a separate context
 * and contexts should never be shared.
 * @author Jacob Northey
 */
public class Context {
    private TemplateRegistry templateRegistry = new BasicTemplateRegistry();
    private int lastTemplateId = -1;
    private Map<String, FastDictionary> dictionaries = new HashMap<String, FastDictionary>();
    private ErrorHandler errorHandler = ErrorHandler.DEFAULT;
    private QName currentApplicationType;
    private List<TemplateRegisteredListener> listeners = Collections.emptyList();
    private boolean traceEnabled;
    private byte[] tempBuffer = new byte[1024 * 32]; // max 32 kB message size

    public Context() {
        dictionaries.put("global", new GlobalFastDictionary());
    }
    public int getTemplateId(MessageTemplate template) {
        if (!templateRegistry.isRegistered(template)) {
            errorHandler.error(FastConstants.D9_TEMPLATE_NOT_REGISTERED, "The template " + template + " has not been registered.");
            return 0;
        }
        return templateRegistry.getId(template);
    }
    public MessageTemplate getTemplate(int templateId) {
        if (!templateRegistry.isRegistered(templateId)) {
            errorHandler.error(FastConstants.D9_TEMPLATE_NOT_REGISTERED, "The template with id " + templateId
                    + " has not been registered.");
            return null;
        }
        return templateRegistry.get(templateId);
    }
    public void registerTemplate(int templateId, MessageTemplate template) {
        templateRegistry.register(templateId, template);
        for (TemplateRegisteredListener listener : listeners) {
            listener.templateRegistered(template, templateId);
        }
    }
    public int getLastTemplateId() {
        return lastTemplateId;
    }
    public void setLastTemplateId(int templateId) {
        lastTemplateId = templateId;
    }
    public void reset() {
        for (FastDictionary dict : dictionaries.values()) {
            dict.reset();
        }
    }
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }
    public void newMessage(MessageTemplate template) {
        currentApplicationType = (template.hasTypeReference()) ? template.getTypeReference() : FastConstants.ANY_TYPE;
    }
    public QName getCurrentApplicationType() {
        return currentApplicationType;
    }
    public TemplateRegistry getTemplateRegistry() {
        return templateRegistry;
    }
    public void setTemplateRegistry(TemplateRegistry registry) {
        this.templateRegistry = registry;
    }
    public boolean isTraceEnabled() {
        return traceEnabled;
    }
    public byte[] getTemporaryBuffer() {
        byte[] pointer = tempBuffer;
        tempBuffer = null;
        return pointer;
    }
    
    public void discardTemporaryBuffer(byte[] buffer) {
        tempBuffer = buffer;
    }
}
