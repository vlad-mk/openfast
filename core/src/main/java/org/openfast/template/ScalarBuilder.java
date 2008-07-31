package org.openfast.template;

import org.lasalletech.entity.QName;
import org.openfast.template.operator.IncrementOperator;

public class ScalarBuilder {
    private Type type;
    private String name;
    private boolean optional;
    private Operator operator = Operator.NONE;

    private ScalarBuilder(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    public Scalar build() {
        return new Scalar(name, type, operator, optional);
    }

    public static ScalarBuilder scalar(String name, Type type) {
        return new ScalarBuilder(name, type);
    }

    public ScalarBuilder increment(QName key, String dictionary, String defaultValue) {
        operator = new IncrementOperator(key, dictionary, defaultValue);
        return this;
    }

    public ScalarBuilder optional() {
        optional = true;
        return this;
    }
}
