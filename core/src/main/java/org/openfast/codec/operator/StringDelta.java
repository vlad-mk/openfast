package org.openfast.codec.operator;

public class StringDelta {
    public static final StringDelta NO_DIFF = new StringDelta(0, "");
    private final int subtractionLength;
    private final String value;

    public StringDelta(int subtractionLength, String value) {
        this.subtractionLength = subtractionLength;
        this.value = value;
    }
    
    public int getSubtractionLength() {
        return subtractionLength;
    }

    public String getValue() {
        return value;
    }

    public String applyTo(String previousValue) {
        if (subtractionLength < 0) {
            int length = (-1 * subtractionLength) - 1;
            return value + previousValue.substring(length, previousValue.length());
        }
        return previousValue.substring(0, previousValue.length() - subtractionLength) + value;
    }
    
    public static StringDelta diff(String newValue, String previousValue) {
        if (previousValue == null || previousValue.length() == 0)
            return new StringDelta(0, newValue);
        if (newValue.equals(previousValue))
            return StringDelta.NO_DIFF;
        String base = previousValue;
        String value = newValue;
        int appendIndex = 0;
        while ((appendIndex < base.length()) && (appendIndex < value.length())
                && (value.charAt(appendIndex) == base.charAt(appendIndex)))
            appendIndex++;
        String append = value.substring(appendIndex);
        int prependIndex = 1;
        while ((prependIndex <= value.length()) && (prependIndex <= base.length())
                && (value.charAt(value.length() - prependIndex) == base.charAt(base.length() - prependIndex)))
            prependIndex++;
        String prepend = value.substring(0, value.length() - prependIndex + 1);
        if (prepend.length() < append.length()) {
            return new StringDelta(prependIndex - base.length() - 2, prepend);
        }
        return new StringDelta(base.length() - appendIndex, append);
    }
    
    @Override
    public String toString() {
        return value + "(" + subtractionLength + ")";
    }
}
