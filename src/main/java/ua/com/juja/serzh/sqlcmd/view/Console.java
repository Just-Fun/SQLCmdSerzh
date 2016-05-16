package ua.com.juja.serzh.sqlcmd.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by serzh on 5/11/16.
 */
public class Console implements View {

    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
