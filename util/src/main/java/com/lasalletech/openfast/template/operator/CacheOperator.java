package com.lasalletech.openfast.template.operator;

import org.lasalletech.entity.QName;
import org.openfast.Fast;
import org.openfast.template.Operator;
import org.openfast.template.operator.DictionaryOperator;

public class CacheOperator extends DictionaryOperator implements Operator {
    private static final long serialVersionUID = 1L;
    private int size;
    
    public CacheOperator(QName key, int size) {
        super(key, Fast.GLOBAL, null);
        this.size = size;
    }

    public Operator copy() {
        return new CacheOperator(getKey(), size);
    }

    public String getDefaultValue() {
        return null;
    }

    public String getName() {
        return "cache";
    }

    public boolean hasDefaultValue() {
        return false;
    }

    public int getSize() {
        return size;
    }
    
    @Override
    public boolean isPrimitive() {
        return false;
    }
}
