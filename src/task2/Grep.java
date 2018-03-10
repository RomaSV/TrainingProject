package task2;

import java.io.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Command-line util grep.
 * Searching text data for lines that contain the  word  given.
 * Options:
 *      -r(regex)      instead of lines that contain  the  word
 *                     prints  lines  that  matches  the  regex
 *                     (CAN NOT BE  USED  WITH  OTHER  OPTIONS)
 *      -i             ignore case
 *      -v             prints lines that DON'T contain the word
 * Example:
 *      -i -v word file_name       (normal search-mode)
 * Or:
 *      -r "expression" file_name  (for regex-mode)
 * Note, that regex itself MUST be quoted.
 */
public class Grep {
    public static void main(String[] args) throws IOException {
        int argsLen = args.length;
        boolean isRegexp = false;
        boolean ignoreCase = false;
        boolean inverse = false;
        String result;

        if (argsLen < 2) {
            throw new IllegalArgumentException(
                    String.format("Expected at least 2 arguments, %d given.", argsLen));
        }
        for (int i = 0; i < argsLen - 2; i++) {
            switch (args[i]) {
                case "-r":
                case "-regex":
                    isRegexp = true;
                    break;
                case "-i":
                    ignoreCase = true;
                    break;
                case "-v":
                    inverse = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown option(s).");
            }
        }

        File input = new File(args[argsLen - 1]);
        BufferedReader reader = new BufferedReader(new FileReader(input));
        result = grep(reader.lines(), args[argsLen - 2], isRegexp, ignoreCase, inverse);
        reader.close();

        System.out.println(result);
    }

    static String grep(Stream<String> lines, Predicate<String> predicate) {
        return lines.filter(predicate).collect(Collectors.joining("\n"));
    }

    static String grep(Stream<String> lines, String expression, boolean r, boolean i, boolean v) {
        if (r && (i || v)) {
            throw new IllegalArgumentException("You can't use -r with any other options.");
        }

        Predicate<String> predicate;
        if (r) {
            predicate = line -> line.matches(expression);
        } else if (i) {
            predicate = line -> v ^ line.toLowerCase().contains(expression.toLowerCase());
        } else {
            predicate = line -> v ^ line.contains(expression);
        }

        return grep(lines, predicate);
    }
}
