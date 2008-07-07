package org.openfast.template;

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
}
