package task2;

import java.io.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Util grep.
 * Searching text data for lines that contain the  word  given.
 * Options:
 *      -r             instead of lines that contain  the  word
 *                     prints  lines  that  contain  the  regex
 *      -i             ignore case
 *      -v             prints lines that DON'T contain the word
 */
public class Grep {
    public static String[] grep(String inputName, String expression, boolean r, boolean i, boolean v) throws IOException {
        File input = new File(inputName);
        Stream<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            lines = reader.lines();

            Predicate<String> predicate;
            if (r) {
                Pattern pattern =
                        i? Pattern.compile(expression, Pattern.CASE_INSENSITIVE): Pattern.compile(expression);
                predicate = line -> {
                    Matcher matcher = pattern.matcher(line);
                    return v ^ matcher.find();
                };
            } else if (i) {
                predicate = line -> v ^ line.toLowerCase().contains(expression.toLowerCase());
            } else {
                predicate = line -> v ^ line.contains(expression);
            }

            return lines.filter(predicate).toArray(String[]::new);
        }
    }

    /**
     * Easier way to launch grep manually.
     * @param inputName - Input file name
     * @param expression - an expression to find in the file
     * @param options - options, e.g. "-r -i -v"
     */
    public static String[] grep (String inputName, String expression, String options) throws IOException {
        boolean r = false;
        boolean i = false;
        boolean v = false;
        String[] ops = options.split(" ");

        for (String o: ops) {
            switch (o) {
                case "-r":
                    r = true;
                    break;
                case "-i":
                    i = true;
                    break;
                case "-v":
                    v = true;
                    break;
                default:
                    throw new IllegalArgumentException("Option " + o + " is unknown.");
            }
        }

        return grep(inputName, expression, r, i, v);
    }

    /** Launch grep with no options. */
    public static String[] grep (String inputName, String expression) throws IOException {
        return grep(inputName, expression, false, false, false);
    }
}
