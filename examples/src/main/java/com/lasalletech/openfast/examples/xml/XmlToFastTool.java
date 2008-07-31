package com.lasalletech.openfast.examples.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.ParseException;
import org.lasalletech.entity.EObject;
import org.lasalletech.entity.EObjectList;
import org.lasalletech.entity.EntityType;
import org.openfast.Message;
import org.openfast.codec.FastEncoder;
import org.openfast.fast.impl.FastImplementation;
import org.openfast.template.MessageTemplate;
import org.openfast.template.loader.XMLMessageTemplateLoader;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XmlToFastTool {
    private MessageTemplate elementTemplate;
    private FastEncoder encoder;
    
    public XmlToFastTool() {
        FastImplementation impl = new Fast11PlusCacheImplementation();
        XMLMessageTemplateLoader loader = new XMLMessageTemplateLoader(impl, false);
        InputStream source = this.getClass().getResourceAsStream("/xml/xmlOverFastTemplates.xml");
        loader.load(source);
        elementTemplate = loader.getTemplateRegistry().get("element");
        encoder = new FastEncoder(impl, loader.getTemplateRegistry());
    }
    /**
     * @param args
     * @throws FileNotFoundException 
     * @throws ParseException 
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: compress <xml file>");
            System.exit(1);
        }
        File xmlFile = new File(args[0]);
        new XmlToFastTool().convert(xmlFile);
    }
    
    public void convert(File xmlFile) throws FileNotFoundException {
        HierarchicalStreamReader reader = new XppDriver().createReader(new FileInputStream(xmlFile));
        Message element = elementTemplate.newObject();
        constructElementMessage(reader, element);
        byte[] buffer = new byte[1024 * 1024];
        int length = encoder.encode(buffer, 0, element);
        System.out.write(buffer, 0, length);
        encoder.reset();
    }
    @SuppressWarnings("unchecked")
    private void constructElementMessage(HierarchicalStreamReader reader, EObject element) {
        element.set(0, reader.getNodeName());
        if (reader.getAttributeCount() > 0) {
            EObjectList attrList = element.createEObjectList(1, reader.getAttributeCount());
            for (int i=0; i<reader.getAttributeCount(); i++) {
                EntityType type = (EntityType) element.getEntity().getField(1).getType();
                EObject attr = type.getEntity().newObject();
                attr.set(0, reader.getAttributeName(i));
                attr.set(1, reader.getAttribute(i));
                attrList.add(attr);
            }
        }
        List<EObject> children = new ArrayList<EObject>();
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            EObject child = elementTemplate.newObject();
            constructElementMessage(reader, child);
            reader.moveUp();
        }
        EObjectList childList = element.createEObjectList(1, children.size());
        for (int i=0; i<children.size(); i++) {
            childList.add(children.get(i));
        }
    }
}
