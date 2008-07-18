package com.lasalletech.openfast.util.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.openfast.Message;
import org.openfast.template.MessageTemplate;
import org.openfast.template.TemplateRegistry;

public class CsvParser {
    private final TemplateRegistry registry;
    private boolean indexBased = true;
    public CsvParser(TemplateRegistry registry) {
        this.registry = registry;
    }
    public void setIndexBased(boolean indexBased) {
        this.indexBased = indexBased;
    }
    public List<Message> parse(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<Message> messages = new ArrayList<Message>();
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] values = parse(line);
            int templateId = Integer.parseInt(values[0]);
            MessageTemplate template = registry.get(templateId);
            Message message = template.newObject();
            for (int i=0;i<values.length-1;i++) {
                if (indexBased) {
                    if (values[i+1] != null)
                        message.getTemplate().getField(i).getType().parse(message, i, values[i+1]);
                }
            }
            messages.add(message);
        }
        return messages;
    }
    private String[] parse(String line) {
        String[] values = line.split(",");
        for (int i=0; i<values.length; i++) {
            values[i] = values[i].trim();
            if ("".equals(values[i]))
                values[i] = null;
        }
        return values;
    }
}
