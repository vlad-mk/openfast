package org.openfast.template;

import org.lasalletech.exom.QName;

public class TailOperator extends DictionaryOperator {
    private static final long serialVersionUID = 1L;
    
    public TailOperator(QName key, String dictionary, String defaultValue) {
        super(key, dictionary, defaultValue);
    }

    public String getName() {
        return "tail";
    }
}
