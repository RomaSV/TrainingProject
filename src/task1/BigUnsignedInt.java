package task1;

import java.util.ArrayList;
import java.util.List;

public class BigUnsignedInt {
    private final List<Byte> value = new ArrayList<>();

    public BigUnsignedInt(String value) {
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException();
        }

        //Clear redundant 0s
        StringBuilder val = new StringBuilder(value);
        while (val.length() > 1 && val.charAt(0) == '0') {
            val.deleteCharAt(0);
        }

        String revValue = val.reverse().toString();
        for (Character digit: revValue.toCharArray()) {
            this.value.add((byte)Character.getNumericValue(digit));
        }
    }

    /** Returns the sum of two numbers. */
    public BigUnsignedInt plus(BigUnsignedInt other) {
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        int maxLen = Math.max(value.size(), other.value.size());
        for (int i = 0; i < maxLen; i++) {
            final int sum;
            if (flag) {
                sum = this.normalizedValue(maxLen).get(i) + other.normalizedValue(maxLen).get(i) + 1;
            } else {
                sum = this.normalizedValue(maxLen).get(i) + other.normalizedValue(maxLen).get(i);
            }

            if (sum > 9) {
                result.append(sum - 10);
                flag = true;
            } else {
                result.append(sum);
                flag = false;
            }
        }
        return new BigUnsignedInt(result.reverse().toString());
    }

    /**
     * A minus operation.
     * @return the (number - argument). Negative results are 0.
     */
    public BigUnsignedInt minus(BigUnsignedInt other) {
        if (!this.greaterThan(other)) {
            return new BigUnsignedInt("0");
        }

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < value.size(); i++) {
            final int sum;
            if (flag) {
                sum = value.get(i) - other.normalizedValue(value.size()).get(i) - 1;
            } else {
                sum = value.get(i) - other.normalizedValue(value.size()).get(i);
            }

            if (sum < 0) {
                result.append(sum + 10);
                flag = true;
            } else {
                result.append(sum);
                flag = false;
            }
        }
        return new BigUnsignedInt(result.reverse().toString());
    }

    /** Returns the multiplication of two numbers. */
    public BigUnsignedInt times(BigUnsignedInt other) {
        if (other.value.size() == 1) {
            return this.multiplyByDigit(other.value.get(0));
        }

        BigUnsignedInt result = new BigUnsignedInt("0");
        for (int i = 0; i < other.value.size(); i++) {
            BigUnsignedInt val = this.multiplyByDigit(other.value.get(i));
            for (int j = 0; j < i; j++) {
                val.value.add(0, (byte) 0);
            }
            result = result.plus(val);
        }
        return result;
    }

    /** Returns true if the number is greater than (and not equals) the argument */
    public boolean greaterThan(BigUnsignedInt other) {
        if (value.size() != other.value.size()) {
            return value.size() > other.value.size();
        }
        for (int i = value.size() - 1; i >= 0; i--) {
            if (!value.get(i).equals(other.value.get(i))) {
                return value.get(i) > other.value.get(i);
            }
        }
        return false;
    }

    /** Returns true if the number is less than (and not equals) the argument */
    public boolean lessThan(BigUnsignedInt other) {
        return (!(greaterThan(other) || equals(other)));
    }

    /** Returns a fixed-size value with 0s at blanks*/
    List<Byte> normalizedValue(int size) {
        if (size < value.size()) {
            return value;
        }

        List<Byte> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i < value.size()) {
                result.add(value.get(i));
            } else {
                result.add((byte) 0);
            }
        }
        return result;
    }

    /** Returns the multiplication of BigUnsignedInt by a single-digit number. */
    BigUnsignedInt multiplyByDigit(byte digit) {
        if (digit > 9) {
            throw new IllegalArgumentException("The argument should be 0-9");
        }
        if (digit == 0) {
            return new BigUnsignedInt("0");
        }

        StringBuilder result = new StringBuilder();
        byte carry = 0;
        for (Byte d : value) {
            final int mult;
            if (carry > 0) {
                mult = d * digit + carry;
            } else {
                mult = d * digit;
            }

            result.append(mult % 10);
            carry = (byte) (mult / 10);
        }
        return new BigUnsignedInt(result.reverse().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BigUnsignedInt) {
            BigUnsignedInt other = (BigUnsignedInt) obj;
            return value.equals(other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Byte i : value) {
            result = ((result << 3) | (result >> 29)) ^ i;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (byte i : value) {
            result.append(i);
        }
        return result.reverse().toString();
    }
}
