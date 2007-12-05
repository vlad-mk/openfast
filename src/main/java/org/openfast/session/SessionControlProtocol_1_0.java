package org.openfast.session;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.ScalarValue;
import org.openfast.codec.Coder;
import org.openfast.error.ErrorCode;
import org.openfast.template.Field;
import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;
import org.openfast.template.TemplateRegistry;
import org.openfast.template.operator.Operator;
import org.openfast.template.type.Type;

class SessionControlProtocol_1_0 extends AbstractSessionControlProtocol {
    static final int FAST_HELLO_TEMPLATE_ID = 16000;
    static final int FAST_ALERT_TEMPLATE_ID = 16001;

	public Session onNewConnection(String serverName, Connection connection) {
		Session session = new Session(connection, this);
		Message message = session.in.readMessage();
        session.out.writeMessage(createHelloMessage(serverName));
		String clientName = message.getString(1);
		session.setClient(new BasicClient(clientName, "unknown"));
        return session;
	}

	public Session connect(String senderName, Connection connection) {
		Session session = new Session(connection, this);
		session.out.writeMessage(createHelloMessage(senderName));
		Message message = session.in.readMessage();
		String serverName = message.getString(1);
		session.setClient(new BasicClient(serverName, "unknown"));
        return session;
	}

	public void onError(Session session, ErrorCode code, String message) {
		session.out.writeMessage(createFastAlertMessage(code));
	}

	public void registerSessionTemplates(TemplateRegistry registry) {
        registry.register(FAST_HELLO_TEMPLATE_ID, FAST_HELLO_TEMPLATE);
        registry.register(FAST_ALERT_TEMPLATE_ID, FAST_ALERT_TEMPLATE);
        registry.register(FAST_RESET_TEMPLATE_ID, FAST_RESET_TEMPLATE);
	}

	public void configureSession(Session session) {
		registerSessionTemplates(session.in.getTemplateRegistry());
		registerSessionTemplates(session.out.getTemplateRegistry());
		
		session.in.addMessageHandler(FAST_RESET_TEMPLATE, RESET_HANDLER);
		session.out.addMessageHandler(FAST_RESET_TEMPLATE, RESET_HANDLER);
	}

    public static Message createFastAlertMessage(ErrorCode code) {
        Message alert = new Message(FAST_ALERT_TEMPLATE);
        alert.setInteger(1, code.getSeverity().getCode());
        alert.setInteger(2, code.getCode());
        alert.setString(4, code.getDescription());

        return alert;
    }
        
    public static Message createHelloMessage(String name) {
        Message message = new Message(FAST_HELLO_TEMPLATE);
        message.setString(1, name);

        return message;
    }
    public final static MessageTemplate FAST_ALERT_TEMPLATE = new MessageTemplate("",
            new Field[] {
                new Scalar("Severity", Type.U32, Operator.NONE,
                    ScalarValue.UNDEFINED, false),
                new Scalar("Code", Type.U32, Operator.NONE, ScalarValue.UNDEFINED, false),
                new Scalar("Value", Type.U32, Operator.NONE,
                    ScalarValue.UNDEFINED, true),
                new Scalar("Description", Type.ASCII, Operator.NONE, ScalarValue.UNDEFINED, false),
            });
    
    public final static MessageTemplate FAST_HELLO_TEMPLATE = new MessageTemplate("",
            new Field[] {
                new Scalar("SenderName", Type.ASCII, Operator.NONE, ScalarValue.UNDEFINED, false)
            });
        
    private static final MessageHandler RESET_HANDLER = new MessageHandler() {
            public void handleMessage(Message readMessage, Context context, Coder coder) {
                coder.reset();
            }
        };

	public void handleMessage(Session session, Message message) {
		if (message.getTemplate().equals(FAST_ALERT_TEMPLATE)) {
			ErrorCode alertCode = ErrorCode.getAlertCode(message);
			if (alertCode.equals(SessionConstants.CLOSE)) {
				session.close(alertCode);
			} else {
				session.getErrorHandler().error(alertCode, message.getString(4));
			}
		}
	}

	public boolean isProtocolMessage(Message message) {
		if (message == null) return false;
		return (message.getTemplate().equals(FAST_ALERT_TEMPLATE)) ||
			   (message.getTemplate().equals(FAST_HELLO_TEMPLATE)) ||
			   (message.getTemplate().equals(FAST_RESET_TEMPLATE));
	}

	public boolean supportsTemplateExchange() {
		return false;
	}

	public Message createTemplateDeclarationMessage(MessageTemplate messageTemplate, int templateId) {
		return null;
	}

	public Message createTemplateDefinitionMessage(MessageTemplate messageTemplate) {
		return null;
	}

	public Message getCloseMessage() {
		return createFastAlertMessage(SessionConstants.CLOSE);
	}
}
