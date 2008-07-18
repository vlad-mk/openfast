package com.lasalletech.openfast.nio.mina;

import java.util.Set;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class FastMessageEncoder implements MessageEncoder {
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        
    }

    public Set<Class<?>> getMessageTypes() {
        return null;
    }

}
