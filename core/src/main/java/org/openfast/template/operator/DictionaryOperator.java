package org.openfast.template.operator;

import org.lasalletech.exom.QName;
import org.openfast.template.AbstractOperator;
import org.openfast.template.Operator;

public abstract class DictionaryOperator extends AbstractOperator implements Operator {
    private static final long serialVersionUID = 1L;
    private final QName key;
    private final String dictionary;
    private String defaultValue;

    protected DictionaryOperator(QName key, String dictionary, String defaultValue) {
        this.key = key;
        this.dictionary = dictionary;
        this.defaultValue = defaultValue;
    }
    
    public QName getKey() {
        return key;
    }
    
    public String getDictionary() {
        return dictionary;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean hasDefaultValue() {
        return defaultValue != null;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
