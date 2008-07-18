package com.lasalletech.openfast.examples.simple;

import java.io.InputStream;
import java.net.InetSocketAddress;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketConnector;
import org.apache.mina.transport.socket.nio.SocketConnectorConfig;
import org.openfast.codec.FastDecoder;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import com.lasalletech.openfast.nio.mina.FastMessageDecoder;
import com.lasalletech.openfast.nio.mina.FastProtocolCodecFactory;

public class SimpleClient {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 7178;

    private final String host;
    private final int port;
    
    public SimpleClient(String host, String port) {
        this.host = (host != null) ? host : DEFAULT_HOST;
        this.port = (port != null) ? Integer.parseInt(port) : DEFAULT_PORT;
    }

    /**
     * @param args
     * @throws ParseException 
     */
    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("h", "host", true, "The host that the server is running on");
        options.addOption("p", "port", true, "The port that the server is listening on");
        CommandLine cl = new BasicParser().parse(options, args);
        new SimpleClient(cl.getOptionValue('h'), cl.getOptionValue('p')).start();
    }

    private void start() {
        final XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.setLoadTemplateIdFromAuxId(true);
        loader.load(resource("simple/integerTemplates.xml"));
        
        ByteBuffer.setUseDirectBuffers(false);
        SocketConnector connector = new SocketConnector();
        SocketConnectorConfig cfg = new SocketConnectorConfig();
        cfg.getFilterChain().addLast("fast", new ProtocolCodecFilter(new FastProtocolCodecFactory()));
        connector.connect(new InetSocketAddress(host, port), new IoHandlerAdapter() {
            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                System.out.println(message);
            }
            
            @Override
            public void sessionCreated(IoSession session) throws Exception {
                session.setAttribute(FastMessageDecoder.DECODER, new FastDecoder(loader.getTemplateRegistry()));
            }
            
            @Override
            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
                cause.printStackTrace();
            }
        }, cfg);
    }

    protected InputStream resource(String url) {
        return this.getClass().getClassLoader().getResourceAsStream(url);
    }
}
