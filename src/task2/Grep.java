package task2;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Grep {
    public static void main(String[] args) throws IOException {
        int argsLen = args.length;
        if (argsLen < 2) {
            throw new IllegalArgumentException(
                    String.format("Expected at least 2 arguments, %d given.", argsLen));
        }
        File input = new File(args[argsLen - 1]);
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String result = grep(reader.lines(), args[argsLen - 2]);
        reader.close();

        System.out.print(result);
    }

    /**
     * Simple search for words.
     * @return string with all lines, which contain the word.
     */
    static String grep(Stream<String> lines, String word) {
        return lines.filter(line -> line.contains(word)).collect(Collectors.joining("\n"));
    }
}
