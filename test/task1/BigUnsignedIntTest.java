package task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BigUnsignedIntTest {
    @Test
    public void plus() {
        assertEquals("580245",
                new BigUnsignedInt("123456").plus(new BigUnsignedInt("456789")).toString());
        String val1 = "123456789000000001";
        String val2 = "999999999";
        assertEquals("123456790000000000",
                new BigUnsignedInt(val1).plus(new BigUnsignedInt(val2)).toString());
    }

    @Test
    public void normalisedValue() {
        assertArrayEquals(new Byte[]{3, 2, 1, 0, 0, 0, 0, 0, 0, 0},
                new BigUnsignedInt("123").normalizedValue(10).toArray());
        assertArrayEquals(new Byte[]{5, 4, 3, 2, 1},
                new BigUnsignedInt("12345").normalizedValue(5).toArray());
        assertArrayEquals(new Byte[]{5, 4, 3, 2, 1},
                new BigUnsignedInt("12345").normalizedValue(3).toArray());
    }
}