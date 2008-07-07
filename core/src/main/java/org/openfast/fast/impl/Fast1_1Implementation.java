package org.openfast.fast.impl;

import java.util.ArrayList;
import java.util.List;
import org.openfast.fast.FastOperators;
import org.openfast.fast.FastTypes;
import org.openfast.template.BasicOperatorRegistry;
import org.openfast.template.BasicTypeRegistry;
import org.openfast.template.OperatorRegistry;
import org.openfast.template.TypeRegistry;
import org.openfast.template.loader.ByteVectorParser;
import org.openfast.template.loader.ComposedDecimalParser;
import org.openfast.template.loader.FieldParser;
import org.openfast.template.loader.GroupParser;
import org.openfast.template.loader.ScalarParser;
import org.openfast.template.loader.SequenceParser;
import org.openfast.template.loader.StringParser;
import org.openfast.template.loader.TemplateRefParser;

public class Fast1_1Implementation extends FastImplementation {
    private List<FieldParser> parsers;
    private TypeRegistry typeRegistry;
    private OperatorRegistry operatorRegistry;

    @Override
    public List<FieldParser> getFieldParsers() {
        if (parsers == null) {
            parsers = new ArrayList<FieldParser>();
            parsers.add(new ScalarParser());
            parsers.add(new GroupParser());
            parsers.add(new SequenceParser());
            parsers.add(new ComposedDecimalParser());
            parsers.add(new StringParser());
            parsers.add(new ByteVectorParser());
            parsers.add(new TemplateRefParser());
        }
        return parsers;
    }

    @Override
    public OperatorRegistry getOperatorRegistry() {
        if (operatorRegistry == null) {
            operatorRegistry = new BasicOperatorRegistry();
            operatorRegistry.register("none", FastOperators.NONE);
            operatorRegistry.register("tail", FastOperators.TAIL);
            operatorRegistry.register("delta", FastOperators.DELTA);
            operatorRegistry.register("default", FastOperators.DEFAULT);
            operatorRegistry.register("constant", FastOperators.CONSTANT);
            operatorRegistry.register("increment", FastOperators.INCREMENT);
            operatorRegistry.register("copy", FastOperators.COPY);
        }
        return operatorRegistry;
    }

    @Override
    public TypeRegistry getTypeRegistry() {
        if (typeRegistry == null) {
            typeRegistry = new BasicTypeRegistry();
            typeRegistry.register("ascii", FastTypes.ASCII);
            typeRegistry.register("byteVector", FastTypes.BYTE_VECTOR);
            typeRegistry.register("decimal", FastTypes.DECIMAL);
            typeRegistry.register("int8", FastTypes.I8);
            typeRegistry.register("int16", FastTypes.I16);
            typeRegistry.register("int32", FastTypes.I32);
            typeRegistry.register("int64", FastTypes.I64);
            typeRegistry.register("string", FastTypes.STRING);
            typeRegistry.register("uInt8", FastTypes.U8);
            typeRegistry.register("uInt16", FastTypes.U16);
            typeRegistry.register("uInt32", FastTypes.U32);
            typeRegistry.register("uInt64", FastTypes.U64);
            typeRegistry.register("unicode", FastTypes.UNICODE);
        }
        return typeRegistry;
    }
}
