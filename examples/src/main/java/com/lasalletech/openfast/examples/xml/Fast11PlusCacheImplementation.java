package com.lasalletech.openfast.examples.xml;

import java.util.List;
import org.openfast.codec.BasicCodecFactory;
import org.openfast.fast.impl.CommonFastImplementation;
import org.openfast.template.loader.OperatorParser;
import com.lasalletech.openfast.codec.operator.CacheCodecFactory;
import com.lasalletech.openfast.template.loader.CacheOperatorParser;

public class Fast11PlusCacheImplementation extends CommonFastImplementation {
    @Override
    protected void addCodecFactories(BasicCodecFactory codecFactory) {
        codecFactory.register("cache", new CacheCodecFactory());
    }
    
    @Override
    protected void addOperatorParsers(List<OperatorParser> operatorParsers) {
        operatorParsers.add(new CacheOperatorParser());
    }
}
