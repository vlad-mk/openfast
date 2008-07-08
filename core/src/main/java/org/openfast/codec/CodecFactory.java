package org.openfast.codec;

import org.openfast.template.MessageTemplate;
import org.openfast.template.Scalar;

public interface CodecFactory {
    MessageCodec createCodec(int id, MessageTemplate template, TypeCodecRegistry typeCodecRegistry);

    ScalarCodec createCodec(Scalar scalar, TypeCodecRegistry typeCodecRegistry);
}
