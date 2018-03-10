package task2;

import java.io.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrepTest {
    @Test
    public void simpleSearch() throws IOException {
        File input = new File("input/input1");

        BufferedReader reader = new BufferedReader(new FileReader(input));
        assertEquals("Lorem ipsum dolor sit amet,",
                Grep.grep(reader.lines(), "Lorem ipsum", false, false, false));
        reader.close();

        reader = new BufferedReader(new FileReader(input));
        assertEquals("sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                Grep.grep(reader.lines(), "dolore", false, false, false));
        reader.close();

        reader = new BufferedReader(new FileReader(input));
        assertEquals("", Grep.grep(reader.lines(), "Лорем", false, false, false));
        reader.close();
    }

    @Test
    public void regexTest() throws IOException {
        File input = new File("input/input2");

        BufferedReader reader = new BufferedReader(new FileReader(input));
        assertEquals("224.0.0.22                                   статический\n" +
                        "224.0.0.251                                  статический\n" +
                        "239.255.255.250                              статический\n" +
                        "192.168.1.1           c8-2b-35-9a-a6-1e      динамический\n" +
                        "192.168.1.132         00-11-92-b3-a8-0d      динамический\n" +
                        "192.168.1.255         ff-ff-ff-ff-ff-ff      статический\n" +
                        "224.0.0.22            01-00-5e-00-00-16      статический\n" +
                        "224.0.0.251           01-00-5e-00-00-fb      статический\n" +
                        "224.0.0.252           01-00-5e-00-00-fc      статический\n" +
                        "239.255.255.250       01-00-5e-7f-ff-fa      статический",
                Grep.grep(reader.lines(),
                        "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*", true, false, false));

        try {
            Grep.grep(reader.lines(),"*[Meaningless Regex]*", true, true, false);
            fail("Exception expected");
        } catch (IllegalArgumentException exception) {
            assertEquals("You can't use -r with any other options.", exception.getMessage());
        }

        reader.close();
    }

    @Test
    public void ignoreCase() throws IOException {
        File input = new File("input/input2");

        BufferedReader reader = new BufferedReader(new FileReader(input));
        assertEquals("192.168.1.1           c8-2b-35-9a-a6-1e      динамический\n" +
                        "192.168.1.132         00-11-92-b3-a8-0d      динамический",
                Grep.grep(reader.lines(),
                        "ДИНАМИЧЕСКИЙ", false, true, false));
        reader.close();
    }

    @Test
    public void inverse() throws IOException {
        File input = new File("input/input2");

        BufferedReader reader = new BufferedReader(new FileReader(input));
        assertEquals("Интерфейс: 127.0.0.1 --- 0x1\n\n" +
                        "адрес в Интернете      Физический адрес      Тип\n\n\n" +
                        "Интерфейс: 192.168.1.133 --- 0x1c\n\n" +
                        "адрес в Интернете     Физический адрес       Тип\n\n" +
                        "192.168.1.1           c8-2b-35-9a-a6-1e      динамический\n" +
                        "192.168.1.132         00-11-92-b3-a8-0d      динамический",
                Grep.grep(reader.lines(), "статический", false, false, true));
        reader.close();
    }
}
