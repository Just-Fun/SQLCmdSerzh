package ua.com.juja.serzh.sqlcmd.integration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by serzh on 5/11/16.
 */
public class ConfigurableInputStream extends InputStream {
    private String line;
    private boolean endLine = false;

    @Override
    public int read() throws IOException {
        if (line.length() == 0) {
            return -1;
        }

        if (endLine) {
            endLine = false;
            return -1;
        }

        char ch = line.charAt(0);
        line = line.substring(1);

        if (ch == '\n') {
            endLine = true;
        }
        return (int) ch;
    }
    void add(String line) {
        if (this.line == null) {
            this.line = line;
        } else {
            this.line += "\n" + line;
        }
    }
}
