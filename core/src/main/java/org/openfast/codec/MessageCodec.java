package org.openfast.codec;

import java.nio.ByteBuffer;
import org.openfast.Context;
import org.openfast.Message;
import org.openfast.util.BitVectorReader;

public interface MessageCodec {
    int encode(byte[] buffer, int offset, Message message, Context context);
    void decode(Message message, ByteBuffer buffer, BitVectorReader reader, Context context);
    int getLength(ByteBuffer buffer, BitVectorReader reader, Context context);
}
