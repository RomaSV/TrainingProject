package task1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BigUnsignedIntTest {
    @Test
    public void plus() {
        assertEquals(new BigUnsignedInt("580245"),
                new BigUnsignedInt("123456").plus(new BigUnsignedInt("456789")));
        String val1 = "123456789000000001";
        String val2 = "999999999";
        assertEquals(new BigUnsignedInt("123456790000000000"),
                new BigUnsignedInt (val1).plus(new BigUnsignedInt(val2)));
    }

    @Test
    public void minus() {
        assertEquals(new BigUnsignedInt("958"),
                new BigUnsignedInt("1000").minus(new BigUnsignedInt("42")));
        assertEquals(new BigUnsignedInt("1"),
                new BigUnsignedInt("10000000000000000000000000000000000000000")
                .minus(new BigUnsignedInt("9999999999999999999999999999999999999999")));
        assertEquals(new BigUnsignedInt("0"),
                new BigUnsignedInt("1").minus(new BigUnsignedInt("20")));
    }

    @Test
    public void times() {
        assertEquals(new BigUnsignedInt("666"),
                new BigUnsignedInt("111").times(new BigUnsignedInt("6")));
        assertEquals(new BigUnsignedInt("1493745"),
                new BigUnsignedInt("12345").times(new BigUnsignedInt("121")));
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

    @Test
    public  void multiplyByDigit() {
        assertEquals(new BigUnsignedInt("861"),
                new BigUnsignedInt("123").multiplyByDigit((byte)7));
        assertEquals(new BigUnsignedInt("0"),
                new BigUnsignedInt("10101010101010101010").multiplyByDigit((byte)0));
    }

    @Test
    public void greaterThan() {
        assertTrue(new BigUnsignedInt("10000000000000000000000").greaterThan(new BigUnsignedInt("42")));
        assertFalse(new BigUnsignedInt("123").greaterThan(new BigUnsignedInt("321")));
        assertTrue(new BigUnsignedInt("123").greaterThan(new BigUnsignedInt("122")));
        assertFalse(new BigUnsignedInt("0").greaterThan(new BigUnsignedInt("0")));
    }

    @Test
    public void lessThan() {
        assertTrue(new BigUnsignedInt("221").lessThan(new BigUnsignedInt("314")));
        assertFalse(new BigUnsignedInt("88005553535").lessThan(new BigUnsignedInt("88005553535")));
    }
}