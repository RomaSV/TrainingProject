package task1;

import java.util.ArrayList;
import java.util.List;

public class BigUnsignedInt {
    private final List<Byte> value = new ArrayList<>();

    public BigUnsignedInt(String value) {
        String revValue = new StringBuilder(value).reverse().toString();
        for (Character digit: revValue.toCharArray()) {
            this.value.add((byte)Character.getNumericValue(digit));
        }
    }

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

    public boolean greaterThan(BigUnsignedInt other) {
        if (value.size() != other.value.size()) return value.size() > other.value.size();
        for (int i = value.size() - 1; i >= 0; i--) {
            if (!value.get(i).equals(other.value.get(i))) {
                return value.get(i) > other.value.get(i);
            }
        }
        return false;
    }

    public boolean lessThan(BigUnsignedInt other) {
        return (!(greaterThan(other) || equals(other)));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof BigUnsignedInt) {
            BigUnsignedInt other = (BigUnsignedInt) obj;
            return value.equals(other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Byte i: value) {
            result = ((result << 3) | (result >> 29)) ^ i;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (byte i: value) {
            result.append(i);
        }
        return result.reverse().toString();
    }

    List<Byte> normalizedValue(int size) {
        if (size < value.size()) return value;

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
}
