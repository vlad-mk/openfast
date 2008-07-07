package org.openfast.template;

import org.lasalletech.exom.QName;

public class IncrementOperator extends DictionaryOperator {
    private static final long serialVersionUID = 1L;
    
    public IncrementOperator(QName key, String dictionary, String defaultValue) {
        super(key, dictionary, defaultValue);
    }


    public String getName() {
        return "increment";
    }
}
