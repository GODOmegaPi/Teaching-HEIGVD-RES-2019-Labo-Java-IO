package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the argument does not
     * contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
        final String WINDOWS = "\r\n";
        final String UNIX = "\n";
        final String MACOS = "\r";

        String[] newLines = {"", ""};

        if (lines.contains(UNIX) && !lines.contains(WINDOWS)) { //UNIX
            newLines[0] = lines.substring(0, lines.indexOf(UNIX) + UNIX.length());
            newLines[1] = lines.substring(lines.indexOf(UNIX) + UNIX.length());
        } else if (lines.contains(MACOS) && !lines.contains(WINDOWS)) { //MACOS
            newLines[0] = lines.substring(0, lines.indexOf(MACOS) + MACOS.length());
            newLines[1] = lines.substring(lines.indexOf(MACOS) + MACOS.length());
        } else if (lines.contains(WINDOWS)) { //Windows
            newLines[0] = lines.substring(0, lines.indexOf(WINDOWS) + WINDOWS.length());
            newLines[1] = lines.substring(lines.indexOf(WINDOWS) + WINDOWS.length());
        } else {
            newLines[1] = lines;
        }

        return newLines;
    }

}
