package ua.com.juja.serzh.sqlcmd.view;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by serzh on 17.05.16.
 */
public class InFile implements View {

    private String pathname;

    public InFile(String pathname) {
        this.pathname = pathname;
    }

    @Override
    public void write(String message) {
        PrintWriter out = null;
        try {
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new PrintWriter(new BufferedWriter(new FileWriter(file.getAbsoluteFile())));
            out.println(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
