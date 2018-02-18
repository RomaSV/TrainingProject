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
