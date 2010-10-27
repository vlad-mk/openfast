package org.openfast.examples.producer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageBlockWriter;
import org.openfast.MessageOutputStream;
import org.openfast.error.ErrorHandler;
import org.openfast.session.Connection;
import org.openfast.session.Endpoint;
import org.openfast.session.FastConnectionException;
import org.openfast.template.TemplateRegistry;
import org.openfast.template.loader.XMLMessageTemplateLoader;

public class MulticastFastMessageProducer extends FastMessageProducer {
    private MessageOutputStream out;

    public MulticastFastMessageProducer(Endpoint endpoint, File templatesFile) throws IOException, FastConnectionException {
		this(endpoint, templatesFile, 0);
	}
	
	public MulticastFastMessageProducer(Endpoint endpoint, File templatesFile, int writeOffset) throws IOException, FastConnectionException {
        super(endpoint, templatesFile, writeOffset);
        Context context = new Context();
        context.setErrorHandler(ErrorHandler.NULL);
        context.setTemplateRegistry(templateRegistry);
        out = new MessageOutputStream(endpoint.connect().getOutputStream(), context);
		out.setBlockWriter(FastMessageProducer.createMessageBlockWriter(writeOffset));
	}

    protected void publish(List messages, List msgOutputStreams) {
        if(out == null) {
            return;
        }
        for(int i = 0; i < messages.size(); ++i) {
            out.writeMessage((Message)messages.get(i), true);
        }
        out.reset();
    }

    public void start() {
        System.out.println("Publishing on " + endpoint);
    }

    public void onConnect(Connection connection) { }
}

