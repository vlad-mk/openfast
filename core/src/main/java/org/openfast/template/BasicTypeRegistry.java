package org.openfast.template;

import java.util.HashMap;
import java.util.Map;

public class BasicTypeRegistry implements TypeRegistry {
    private Map<String, Type> types = new HashMap<String, Type>();

    public Type get(String name) {
        return types.get(name);
    }

    public boolean isDefined(String typeName) {
        return types.containsKey(typeName);
    }

    public void register(String name, Type type) {
        types.put(name, type);
    }
}
