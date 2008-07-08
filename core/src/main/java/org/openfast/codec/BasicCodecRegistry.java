package org.openfast.codec;

public class BasicCodecRegistry implements MessageCodecRegistry {
    private Entry[] codecEntries = new Entry[256];
    public BasicCodecRegistry() {
        
    }
    public MessageCodec get(int id) {
        int index = id & 0xff;
        if (codecEntries[index] == null) return null;
        Entry entry = codecEntries[index];
        while (entry.id != id) {
            if (entry.next == null) return null;
            entry = entry.next;
        }
        return entry.codec;
    }

    public boolean isRegistered(int id) {
        int index = id & 0xff;
        if (codecEntries[index] == null) return false;
        Entry entry = codecEntries[index];
        while (entry.id != id) {
            if (entry.next == null) return false;
            entry = entry.next;
        }
        return true;
    }

    public void register(int id, MessageCodec codec) {
        int index = id & 0xff;
        if (codecEntries[index] != null) {
            codecEntries[index] = new Entry(id, codec);
        } else {
            codecEntries[index] = new Entry(id, codec, codecEntries[index]);
        }
    }
    
    private class Entry {
        int id;
        Entry next;
        MessageCodec codec;
        public Entry(int id, MessageCodec codec) {
            this(id, codec, null);
        }
        public Entry(int id, MessageCodec codec, Entry entry) {
            this.id = id;
            this.codec = codec;
            this.next = entry;
        }
    }
}
