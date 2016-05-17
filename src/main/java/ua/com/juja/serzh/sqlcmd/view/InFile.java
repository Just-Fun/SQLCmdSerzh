package ua.com.juja.serzh.sqlcmd.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        try {
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write(message);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
