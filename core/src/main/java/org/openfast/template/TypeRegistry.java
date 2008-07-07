package org.openfast.template;

public interface TypeRegistry {
    Type get(String name);
    void register(String name, Type type);
    boolean isDefined(String typeName);
}
