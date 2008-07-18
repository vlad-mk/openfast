package org.openfast.codec;

import org.openfast.Context;
import org.openfast.Message;
import org.openfast.util.BitVectorReader;

public interface MessageCodec {
    int encode(byte[] buffer, int offset, Message message, Context context);
    void decode(Message message, byte[] buffer, int offset, BitVectorReader reader, Context context);
    int getLength(byte[] buffer, int offset, BitVectorReader reader, Context context);
}
