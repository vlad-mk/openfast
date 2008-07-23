package com.lasalletech.openfast.examples.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.List;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.common.ThreadModel;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.MessageHandler;
import org.openfast.codec.Coder;
import org.openfast.codec.FastEncoder;
import org.openfast.template.MessageTemplate;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import com.lasalletech.openfast.nio.mina.FastMessageEncoder;
import com.lasalletech.openfast.nio.mina.FastProtocolCodecFactory;
import com.lasalletech.openfast.util.csv.CsvParser;

public class SimpleServer {
    private static final int DEFAULT_PORT = 7178;
    private final int port;
    public SimpleServer(String port) {
        this.port = (port == null) ? DEFAULT_PORT : Integer.parseInt(port);
    }
    /**
     * @param args
     * @throws ParseException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws ParseException, FileNotFoundException {
        Options options = new Options();
        options.addOption("p", "port", true, "The port that this server runs on");
        CommandLine cl = new BasicParser().parse(options, args);
        new SimpleServer(cl.getOptionValue('p')).start();
    }

    private void start() throws FileNotFoundException {
        final XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader();
        loader.setLoadTemplateIdFromAuxId(true);
        File[] templateFiles = getFiles("/simple/templates");
        for (File templateFile : templateFiles)
            loader.load(new FileInputStream(templateFile));
        final MessageTemplate disconnectTemplate = loader.getTemplateRegistry().get("disconnect");
        final MessageTemplate resetTemplate = loader.getTemplateRegistry().get("reset");
        final MessageTemplate testFileTemplate = loader.getTemplateRegistry().get("testFile");
        final Message disconnect = disconnectTemplate.newObject();
        final Message reset = resetTemplate.newObject();
        final CsvParser csvParser = new CsvParser(loader.getTemplateRegistry());
        
        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

        IoAcceptor acceptor = new SocketAcceptor();
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        cfg.getFilterChain().addFirst( "codec", new ProtocolCodecFilter( new FastProtocolCodecFactory()));
        cfg.setThreadModel(ThreadModel.MANUAL);

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
                    FastEncoder encoder = new FastEncoder(loader.getTemplateRegistry());
                    encoder.registerMessageHandler(resetTemplate, new MessageHandler() {
                        public void handleMessage(Message message, Context context, Coder coder) {
                            coder.reset();
                        }});
                    session.setAttribute(FastMessageEncoder.ENCODER, encoder);
                    System.out.println("Client connected.");
                    File[] csvFiles = getFiles("/simple/data");
                    for (File csvFile : csvFiles) {
                        session.write(reset);
                        Message fileName = testFileTemplate.newObject();
                        fileName.set(0, csvFile.getName());
                        session.write(fileName);
                        List<Message> messages = csvParser.parse(new FileInputStream(csvFile));
                        for (Message message : messages) {
                            System.out.println(message);
                            session.write(message);
                        }
                    }
                    System.out.println(disconnect);
                    session.write(disconnect);
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

    protected File[] getFiles(String path) {
        URL url = this.getClass().getResource(path);
        File dir = new File(url.getFile());
        return dir.listFiles();
    }
}
