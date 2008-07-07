package org.openfast.template;

import java.util.HashMap;
import java.util.Map;
import org.openfast.fast.FastOperators;

public class BasicOperatorRegistry implements OperatorRegistry {
    private Map<String, Operator> operators = new HashMap<String, Operator>();

    public Operator get(String name) {
        return operators.get(name);
    }

    public Operator getDefault() {
        return FastOperators.NONE;
    }

    public void register(String name, Operator operator) {
        operators.put(name, operator);
    }

    public boolean isDefined(String name) {
        return operators.containsKey(name);
    }
}
