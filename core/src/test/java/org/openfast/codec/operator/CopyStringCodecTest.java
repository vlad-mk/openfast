package org.openfast.codec.operator;

import static org.openfast.codec.operator.FastStringOperatorTestHarness.INITIAL_VALUE;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.KEY;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.NO_INITIAL_VALUE;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.NULL;
import static org.openfast.codec.operator.FastStringOperatorTestHarness.UNDEFINED;
import org.openfast.Fast;
import org.openfast.fast.FastTypes;
import org.openfast.template.Scalar;
import org.openfast.template.operator.CopyOperator;
import org.openfast.test.OpenFastTestCase;

public class CopyStringCodecTest extends OpenFastTestCase {
    Scalar noDefaultScalar = new Scalar("", FastTypes.ASCII, new CopyOperator(KEY, Fast.GLOBAL, null), true);
    Scalar defaultScalar = new Scalar("", FastTypes.ASCII, new CopyOperator(FastOperatorTestHarness.KEY, Fast.GLOBAL, "A"), true);
    
    FastStringOperatorTestHarness harness = new FastStringOperatorTestHarness(noDefaultScalar, defaultScalar);
    
    public void testDecode() {
        harness.assertDecodeNull(  NO_INITIAL_VALUE, UNDEFINED);
        harness.assertDecodeNull(  INITIAL_VALUE,    UNDEFINED, "10000000");
        harness.assertDecodeNull(  INITIAL_VALUE,    NULL);
        harness.assertDecodeNull(  INITIAL_VALUE,    "A",        "10000000");
        harness.assertDecode ("A", NO_INITIAL_VALUE, UNDEFINED, "11000001");
        harness.assertDecode ("A", INITIAL_VALUE,    UNDEFINED);
        harness.assertDecode ("C", INITIAL_VALUE,    UNDEFINED, "11000011");
        harness.assertDecode ("C", INITIAL_VALUE,    NULL,      "11000011");
        harness.assertDecode ("A", INITIAL_VALUE,    "A");
        harness.assertDecode ("B", INITIAL_VALUE,    "B");
    }

    public void testEncode() {
        harness.assertEncode("10000000", INITIAL_VALUE, UNDEFINED);
        harness.assertEncodeEmpty(INITIAL_VALUE, UNDEFINED, "A");
    }
}
