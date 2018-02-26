package task1;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class BigUnsignedInt {
    private final List<Byte> value = new ArrayList<>();

    public BigUnsignedInt(String value) {
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("The argument must be an integer number.");
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

    public BigUnsignedInt(Integer value) {
        this(value.toString());
    }

    /** Returns the sum of two numbers. */
    public BigUnsignedInt plus(BigUnsignedInt other) {
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        int maxLen = Math.max(value.size(), other.value.size());
        List<Byte> thisNormalized = this.normalizedValue(maxLen);
        List<Byte> otherNormalized = other.normalizedValue(maxLen);

        for (int i = 0; i < maxLen; i++) {
            final int sum;
            if (flag) {
                sum = thisNormalized.get(i) + otherNormalized.get(i) + 1;
            } else {
                sum = thisNormalized.get(i) + otherNormalized.get(i);
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
        List<Byte> otherNormalized = other.normalizedValue(value.size());
        for (int i = 0; i < value.size(); i++) {
            final int sum;
            if (flag) {
                sum = value.get(i) - otherNormalized.get(i) - 1;
            } else {
                sum = value.get(i) - otherNormalized.get(i);
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

    /** Returns integer-division of two numbers. */
    public BigUnsignedInt divideBy (BigUnsignedInt other) {
        return divWithMod(other).getKey();
    }

    public BigUnsignedInt mod (BigUnsignedInt other) {
        return divWithMod(other).getValue();
    }

    /** Returns true if the number is greater than (and not equals) the argument. */
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

    /** Returns true if the number is less than (and not equals) the argument. */
    public boolean lessThan(BigUnsignedInt other) {
        return (!(greaterThan(other) || equals(other)));
    }

    /** Returns a fixed-size value with 0s at blanks. */
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

    /**
     * @return the multiplication of BigUnsignedInt by a single-digit number.
     * @throws IllegalArgumentException if the argument is not a digit.
     */
    BigUnsignedInt multiplyByDigit(byte digit) {
        if (digit > 9) {
            throw new IllegalArgumentException("The argument should be 0-9.");
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

    /**
     * Division with remainder.
     * @return pair, where the key is division result and the value is mod result.
     * @throws ArithmeticException if the argument is 0.
     */
    Pair<BigUnsignedInt, BigUnsignedInt> divWithMod(BigUnsignedInt other) {
        if (this.lessThan(other)) {
            return new Pair<>(new BigUnsignedInt("0"), new BigUnsignedInt(this.toString()));
        }
        if (other.equals(new BigUnsignedInt("0"))) {
            throw new ArithmeticException("You're trying to divide by zero.");
        }

        StringBuilder result = new StringBuilder();
        String modValue = this.toString().substring(0, other.value.size() - 1);
        BigUnsignedInt mod = (other.value.size() != 1)? new BigUnsignedInt(modValue): new BigUnsignedInt("0");
        for (int i = value.size() - other.value.size(); i >= 0; i--) {
            BigUnsignedInt tmp = new BigUnsignedInt(mod.toString() + value.get(i));
            Byte digit = 0;
            if (!tmp.lessThan(other)) {
                for (int j = 1; j <= 9; j++) {
                    tmp = tmp.minus(other);
                    if (tmp.lessThan(other)) {
                        digit = (byte) j;
                        mod = tmp;
                        break;
                    }
                }
            } else {
                mod = tmp;
            }
            result.append(digit);
        }
        return new Pair<>(new BigUnsignedInt(result.toString()), mod);
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
