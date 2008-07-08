package org.openfast.codec;

public interface MessageCodecRegistry {
    boolean isRegistered(int id);
    void register(int id, MessageCodec createCodec);
    MessageCodec get(int id);
}
