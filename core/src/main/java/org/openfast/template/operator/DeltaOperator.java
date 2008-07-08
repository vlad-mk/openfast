package org.openfast.template.operator;

import org.lasalletech.exom.QName;

public class DeltaOperator extends DictionaryOperator {
    private static final long serialVersionUID = 1L;

    public DeltaOperator(QName key, String dictionary, String defaultValue) {
        super(key, dictionary, defaultValue);
    }


    public String getName() {
        return "delta";
    }
}
