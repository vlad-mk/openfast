package org.openfast.template.operator;

import org.openfast.template.Operator;

public class DefaultOperator implements Operator {
    private static final long serialVersionUID = 1L;
    private String defaultValue;

    public DefaultOperator(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public String getName() {
        return "default";
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }
    
}
