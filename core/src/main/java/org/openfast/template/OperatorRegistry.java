package org.openfast.template;

public interface OperatorRegistry {
    Operator getDefault();
    Operator get(String name);
    void register(String name, Operator operator);
    boolean isDefined(String name);
}
