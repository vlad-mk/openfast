package org.openfast.codec;

import java.nio.ByteBuffer;
import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.util.BitVectorReader;

public interface GroupCodec extends FieldCodec {
    int encode(EObject object, byte[] buffer, int offset, Context context);
    EObject decode(ByteBuffer buffer, BitVectorReader pmapReader, Context context);
}
