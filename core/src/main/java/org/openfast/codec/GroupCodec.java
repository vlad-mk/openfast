package org.openfast.codec;

import org.lasalletech.entity.EObject;
import org.openfast.Context;
import org.openfast.util.BitVectorReader;

public interface GroupCodec extends FieldCodec {
    int encode(EObject object, byte[] buffer, int offset, Context context);
    EObject decode(byte[] buffer, int offset, BitVectorReader pmapReader, Context context);
}
