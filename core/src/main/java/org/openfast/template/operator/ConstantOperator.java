package org.openfast.template.operator;

import org.openfast.template.Operator;

public class ConstantOperator implements Operator {
    private static final long serialVersionUID = 1L;
    
    private final String defaultValue;

    public String getDefaultValue() {
        return defaultValue;
    }

    public ConstantOperator(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public String getName() {
        return "constant";
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }
}
