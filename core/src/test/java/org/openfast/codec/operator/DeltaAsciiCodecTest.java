package org.openfast.codec.operator;

import static org.openfast.codec.operator.FastOperatorTestHarness.KEY;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.INITIAL_VALUE;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.NO_INITIAL_VALUE;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.UNDEFINED;
import org.openfast.Fast;
import org.openfast.codec.ScalarCodec;
import org.openfast.codec.type.FastTypeCodecs;
import org.openfast.template.operator.DeltaOperator;
import org.openfast.template.operator.DictionaryOperator;
import junit.framework.TestCase;

public class DeltaAsciiCodecTest extends TestCase {
    private DictionaryOperator noDefault = new DeltaOperator(KEY, Fast.GLOBAL, null);
    private DictionaryOperator withDefault = new DeltaOperator(KEY, Fast.GLOBAL, "ABCD");
    private ScalarCodec noDefaultCodec = new DeltaAsciiCodec(noDefault, FastTypeCodecs.NULLABLE_SIGNED_INTEGER, FastTypeCodecs.ASCII_STRING);
    private ScalarCodec withDefaultCodec = new DeltaAsciiCodec(withDefault, FastTypeCodecs.NULLABLE_SIGNED_INTEGER, FastTypeCodecs.ASCII_STRING);;
    FastStringOperatorTestHarness harness = new FastStringOperatorTestHarness(noDefaultCodec, withDefaultCodec);

    public void testDecode() {
        harness.assertDecodeNull(NO_INITIAL_VALUE, UNDEFINED, "10000000");
        harness.assertDecode("ABCD", NO_INITIAL_VALUE, UNDEFINED, "10000001 01000001 01000010 01000011 11000100");
        harness.assertDecode("ABCDEFGH", INITIAL_VALUE, UNDEFINED, "10000001 01000101 01000110 01000111 11001000");
    }

    public void testEncode() {
        harness.assertEncode("10000000", NO_INITIAL_VALUE, UNDEFINED);
        harness.assertEncode("10000001 01000001 01000010 01000011 11000100", NO_INITIAL_VALUE, UNDEFINED, "ABCD");
        harness.assertEncode("10000001 01000101 01000110 01000111 11001000", INITIAL_VALUE, UNDEFINED, "ABCDEFGH");
        harness.assertEncode("10000011 01000101 11000110", INITIAL_VALUE, UNDEFINED, "ABEF");
        harness.assertEncode("11111111 10000000", INITIAL_VALUE, UNDEFINED, "BCD");
    }
}
