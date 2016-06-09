package ua.com.juja.serzh.sqlcmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by serzh on 6/9/16.
 */
public class ForTests {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("Luck", "yoda"));
        List<String> list1 = new ArrayList<>(Arrays.asList("Luck", "yoda"));

        for (String l : list) {
            if (!Character.isLowerCase(l.charAt(0))) {
                continue;
            }
            list1.add(l);
        }

        System.out.println(list.toString());
        System.out.println(list1.toString());
    }
}
