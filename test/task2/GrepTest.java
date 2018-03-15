package task2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class GrepTest {
    @Test
    public void simpleSearch() throws IOException {
        String[] expected =  new String[]{
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
        };
        assertArrayEquals(expected, Grep.grep("input/input1", "dolore"));
    }

    @Test
    public void wordNotFound() throws IOException {
        assertArrayEquals(new String[]{}, Grep.grep("input/input1", "Лорем"));
    }

    @Test
    public void regexTest() throws IOException {
        String[] expected =  new String[]{
                "Интерфейс: 127.0.0.1 --- 0x1",
                "224.0.0.22                                   статический",
                "224.0.0.251                                  статический",
                "239.255.255.250                              статический",
                "Интерфейс: 192.168.1.133 --- 0x1c",
                "192.168.1.1           c8-2b-35-9a-a6-1e      динамический",
                "192.168.1.132         00-11-92-b3-a8-0d      динамический",
                "192.168.1.255         ff-ff-ff-ff-ff-ff      статический",
                "224.0.0.22            01-00-5e-00-00-16      статический",
                "224.0.0.251           01-00-5e-00-00-fb      статический",
                "224.0.0.252           01-00-5e-00-00-fc      статический",
                "239.255.255.250       01-00-5e-7f-ff-fa      статический"
        };
        assertArrayEquals(expected, Grep.grep("input/input2", "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", "-r"));
    }

    @Test
    public void ignoreCase() throws IOException {
        String[] expected =  new String[]{
                "192.168.1.1           c8-2b-35-9a-a6-1e      динамический",
                "192.168.1.132         00-11-92-b3-a8-0d      динамический"
        };
        assertArrayEquals(expected, Grep.grep("input/input2", "ДИНАМИЧЕСКИЙ", "-i"));
    }

    @Test
    public void inverse() throws IOException {
        String[] expected =  new String[]{
                "Интерфейс: 127.0.0.1 --- 0x1",
                "",
                "адрес в Интернете      Физический адрес      Тип",
                "",
                "",
                "Интерфейс: 192.168.1.133 --- 0x1c",
                "",
                "адрес в Интернете     Физический адрес       Тип",
                "",
                "192.168.1.1           c8-2b-35-9a-a6-1e      динамический",
                "192.168.1.132         00-11-92-b3-a8-0d      динамический"
        };
        assertArrayEquals(expected, Grep.grep("input/input2", "статический", "-v"));
    }

    @Test
    public void inverseAndIgnoreCase() throws IOException {
        String[] expected =  new String[]{
                "Интерфейс: 127.0.0.1 --- 0x1",
                "",
                "адрес в Интернете      Физический адрес      Тип",
                "",
                "",
                "Интерфейс: 192.168.1.133 --- 0x1c",
                "",
                "адрес в Интернете     Физический адрес       Тип",
                "",
                "192.168.1.1           c8-2b-35-9a-a6-1e      динамический",
                "192.168.1.132         00-11-92-b3-a8-0d      динамический"
        };
        assertArrayEquals(expected, Grep.grep("input/input2", "СтАтИчЕсКиЙ", "-i -v"));
    }

    @Test
    public void inverseRegex() throws IOException {
        String[] expected =  new String[]{
                "",
                "адрес в Интернете      Физический адрес      Тип",
                "",
                "",
                "",
                "адрес в Интернете     Физический адрес       Тип",
                "",
        };
        assertArrayEquals(expected, Grep.grep("input/input2", "\\d", "-v -r"));
    }

    @Test
    public void allOptions() throws IOException {
        String[] expected =  new String[]{
                "HEX code with numbers in it: #F0F0F0",
                "HEX code without a # in front of the code: FFF000"
        };
        String expression = "([a-f]{2})([a-f]{2})([a-f]{2})";
        assertArrayEquals(expected, Grep.grep("input/input3", expression, true, true, true));
    }

    @Test
    public void invalidArgs() throws IOException {
        try {
            Grep.grep("input/input3", "", "-r -l -v");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Option -l is unknown.", e.getMessage());
        }
    }
}
