package org.openfast.fast.impl;

import java.util.ArrayList;
import java.util.List;
import org.openfast.codec.BasicCodecFactory;
import org.openfast.codec.BasicTypeCodecRegistry;
import org.openfast.codec.CodecFactory;
import org.openfast.codec.TypeCodecRegistry;
import org.openfast.codec.type.FastTypeCodecs;
import org.openfast.fast.FastTypes;
import org.openfast.template.BasicTypeRegistry;
import org.openfast.template.TypeRegistry;
import org.openfast.template.loader.ByteVectorParser;
import org.openfast.template.loader.ComposedDecimalParser;
import org.openfast.template.loader.ConstantOperatorParser;
import org.openfast.template.loader.CopyOperatorParser;
import org.openfast.template.loader.DefaultOperatorParser;
import org.openfast.template.loader.DeltaOperatorParser;
import org.openfast.template.loader.FieldParser;
import org.openfast.template.loader.GroupParser;
import org.openfast.template.loader.IncrementOperatorParser;
import org.openfast.template.loader.OperatorParser;
import org.openfast.template.loader.ScalarParser;
import org.openfast.template.loader.SequenceParser;
import org.openfast.template.loader.StringParser;
import org.openfast.template.loader.TailOperatorParser;
import org.openfast.template.loader.TemplateRefParser;

public class Fast1_1Implementation extends FastImplementation {
    private List<FieldParser> parsers;
    private TypeRegistry typeRegistry;
    private List<OperatorParser> operatorParsers;
    private BasicTypeCodecRegistry typeCodecRegistry;

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
    public List<OperatorParser> getOperatorParsers() {
        if (operatorParsers == null) {
            operatorParsers = new ArrayList<OperatorParser>();
            operatorParsers.add(new DefaultOperatorParser());
            operatorParsers.add(new TailOperatorParser());
            operatorParsers.add(new DeltaOperatorParser());
            operatorParsers.add(new ConstantOperatorParser());
            operatorParsers.add(new IncrementOperatorParser());
            operatorParsers.add(new CopyOperatorParser());
        }
        return operatorParsers;
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

    @Override
    public CodecFactory getCodecFactory() {
        return new BasicCodecFactory();
    }

    @Override
    public TypeCodecRegistry getTypeCodecRegistry() {
        if (typeCodecRegistry == null) {
            typeCodecRegistry = new BasicTypeCodecRegistry();
            typeCodecRegistry.register(FastTypes.I8,  FastTypeCodecs.SIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.I16, FastTypeCodecs.SIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.I32, FastTypeCodecs.SIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.I64, FastTypeCodecs.SIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.U8,  FastTypeCodecs.UNSIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.U16, FastTypeCodecs.UNSIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.U32, FastTypeCodecs.UNSIGNED_INTEGER);
            typeCodecRegistry.register(FastTypes.U64, FastTypeCodecs.UNSIGNED_INTEGER);
        }
        return typeCodecRegistry;
    }
}
