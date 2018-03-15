package task2;

import java.io.*;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Command-line grep launcher.
 * Searching text data for lines that contain the  word  given.
 * Options:
 *      -r             instead of lines that contain  the  word
 *                     prints  lines  that  contain  the  regex
 *      -i             ignore case
 *      -v             prints lines that DON'T contain the word
 * Example:
 *      -r -v "expression" file_name
 * Note, that expression  if  it's  a  regex  MUST  be  quoted.
 */
public class GrepLauncher {
    @Option(name = "-r", metaVar = "Regex", usage = "Search by regex")
    private boolean regex;

    @Option(name = "-i", metaVar = "IgnoreCase", usage = "Ignore case")
    private boolean ignoreCase;

    @Option(name = "-v", metaVar = "Inverse", usage = "Inverse result")
    private boolean inverse;

    @Argument(required = true, metaVar = "Expression", usage = "A word or regex to find in the file")
    private String expression;

    @Argument(required = true, metaVar = "InputName", index = 1, usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new GrepLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Expected arguments: [-r][-i][-v] Expression InputName");
            parser.printUsage(System.err);
            return;
        }

        try {
            String[] result = Grep.grep(inputFileName, expression, regex, ignoreCase, inverse);
            for (String line: result) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
