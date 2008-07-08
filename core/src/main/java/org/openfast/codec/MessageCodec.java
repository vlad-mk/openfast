package org.openfast.codec;

import org.openfast.Context;
import org.openfast.Message;

public interface MessageCodec {

    int encode(byte[] buffer, int offset, Message message, Context context);

}
