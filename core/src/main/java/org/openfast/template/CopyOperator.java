package org.openfast.template;

import org.lasalletech.exom.QName;

public class CopyOperator extends DictionaryOperator implements Operator {
    private static final long serialVersionUID = 1L;

    public CopyOperator(QName key, String dictionary, String defaultValue) {
        super(key, dictionary, defaultValue);
    }

    public String getName() {
        return "copy";
    }
}
