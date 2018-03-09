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
                Grep.grep(reader.lines(), "Lorem ipsum"));
        reader.close();

        reader = new BufferedReader(new FileReader(input));
        assertEquals("sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
                        "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                Grep.grep(reader.lines(), "dolore"));
        reader.close();

        reader = new BufferedReader(new FileReader(input));
        assertEquals("", Grep.grep(reader.lines(), "Лорем"));
        reader.close();
    }
}
