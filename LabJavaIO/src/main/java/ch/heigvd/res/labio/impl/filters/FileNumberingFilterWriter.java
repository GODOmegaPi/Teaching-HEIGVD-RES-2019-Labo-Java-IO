package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;

import javax.rmi.CORBA.Util;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    private int lineNb = 1;
    private String previousChar = "";

    @Override
    public void write(String str, int off, int len) throws IOException {
        str = str.substring(off, off + len);

        for (char c : str.toCharArray()) {
            if(previousChar.isEmpty()){
                super.out.write(lineNb + "\t");
                previousChar = lineNb + "\t";
                lineNb++;
            }
            if((previousChar + String.valueOf(c)).equalsIgnoreCase(Utils.WINDOWS)){
                super.out.write(previousChar + c);
                previousChar = "";
            } else if(String.valueOf(c).equalsIgnoreCase(Utils.UNIX)){
                super.out.write(c);
                previousChar = "";
            } else if (String.valueOf(c).equalsIgnoreCase(Utils.MACOS)) {
                previousChar = String.valueOf(c);
            } else if (previousChar.equalsIgnoreCase(Utils.MACOS)) {
                super.out.write(previousChar);

                super.out.write(lineNb + "\t");
                previousChar = lineNb + "\t";
                lineNb++;

                super.out.write(c);
                previousChar = String.valueOf(c);
            }
            else {
                super.out.write(c);
                previousChar = String.valueOf(c);
            }
        }

        if(previousChar.isEmpty()){
            super.out.write(lineNb + "\t");
            previousChar = lineNb + "\t";
            lineNb++;
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        write(String.copyValueOf(cbuf), off, len);
    }

    @Override
    public void write(int c) throws IOException {
        write(String.valueOf((char) c), 0, 1);
    }

}
