package com.lasalletech.openfast.examples.simple;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.openfast.Message;
import org.openfast.codec.FastEncoder;
import org.openfast.template.MessageTemplate;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import com.lasalletech.openfast.nio.mina.FastMessageEncoder;
import com.lasalletech.openfast.nio.mina.FastProtocolCodecFactory;

public class SimpleServer {
    private static final int DEFAULT_PORT = 7178;
    private final int port;
    public SimpleServer(String port) {
        this.port = (port == null) ? DEFAULT_PORT : Integer.parseInt(port);
    }
    /**
     * @param args
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("p", "port", true, "The port that this server runs on");
        CommandLine cl = new BasicParser().parse(options, args);
        new SimpleServer(cl.getOptionValue('p')).start();
    }

    private void start() {
        final XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.setLoadTemplateIdFromAuxId(true);
        loader.load(resource("simple/integerTemplates.xml"));
        final MessageTemplate template = loader.getTemplateRegistry().getTemplates()[0];
        
        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

        IoAcceptor acceptor = new SocketAcceptor();
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        cfg.getFilterChain().addFirst( "codec", new ProtocolCodecFilter( new FastProtocolCodecFactory()));

        try {
            acceptor.bind(new InetSocketAddress(port), new IoHandlerAdapter() {
                @Override
                public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
                    cause.printStackTrace();
                }
                
                @Override
                public void messageReceived(IoSession session, Object message) throws Exception {
                    System.out.println(message);
                }
                @Override
                public void sessionCreated(IoSession session) throws Exception {
                    session.setAttribute(FastMessageEncoder.ENCODER, new FastEncoder(loader.getTemplateRegistry()));
                    System.out.println("Client connected.");
                    for (int i=0; i<10; i++) {
                        Message message = new Message(template);
                        message.set(0, i+1);
                        System.out.println(message);
                        session.write(message);
                    }
//                    Thread.sleep(100);
//                    session.close();
                }
                @Override
                public void sessionClosed(IoSession session) throws Exception {
                    System.out.println("Client disconnected.");
                }
            }, cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected InputStream resource(String url) {
        return this.getClass().getClassLoader().getResourceAsStream(url);
    }
}
