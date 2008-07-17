package org.openfast.template.operator;

import org.lasalletech.exom.QName;
import org.openfast.template.Operator;

public class IncrementOperator extends DictionaryOperator {
    private static final long serialVersionUID = 1L;
    
    public IncrementOperator(QName key, String dictionary, String defaultValue) {
        super(key, dictionary, defaultValue);
    }


    public String getName() {
        return "increment";
    }


    public Operator copy() {
        return new IncrementOperator(getKey(), getDictionary(), getDefaultValue());
    }
}
