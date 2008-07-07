package org.openfast.template;

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
    
}
