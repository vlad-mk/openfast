package org.openfast.template.operator;

import org.lasalletech.exom.QName;
import org.openfast.template.Operator;

public class TailOperator extends DictionaryOperator {
    private static final long serialVersionUID = 1L;
    
    public TailOperator(QName key, String dictionary, String defaultValue) {
        super(key, dictionary, defaultValue);
    }

    public String getName() {
        return "tail";
    }

    public Operator copy() {
        return new TailOperator(getKey(), getDictionary(), getDefaultValue());
    }
}
