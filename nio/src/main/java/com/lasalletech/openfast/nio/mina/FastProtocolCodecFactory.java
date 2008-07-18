package com.lasalletech.openfast.nio.mina;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

public class FastProtocolCodecFactory extends DemuxingProtocolCodecFactory {
    public FastProtocolCodecFactory() {
        register(FastMessageDecoder.class);
        register(FastMessageEncoder.class);
    }
}
