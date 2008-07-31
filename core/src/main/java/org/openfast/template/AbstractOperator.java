package org.openfast.template;


public abstract class AbstractOperator implements Operator {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return getName();
    }
    
    public boolean isPrimitive() {
        return true;
    }
}
