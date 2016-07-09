package ua.com.juja.serzh.sqlcmd;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by serzh on 6/9/16.
 */
public class ForTests {
    public static void main(String[] args) {

        List<String> input = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            input.add(String.valueOf(i));
        }
        long start = System.currentTimeMillis();

//        String values = getFormatted(input, "%s,");
        String values = getJoiner(input, ",");
        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println(time);
        System.out.println(values);
        System.out.println(values.length());

    }

    private static String getFormatted(List<String> input, String format) {
        StringBuilder values = new StringBuilder("");
        for (String value : input) {
            values.append(String.format(format, value));
        }
        return values.substring(0, values.length() - 1);
    }

    private static String getJoiner(List<String> input, String format) {
        StringJoiner joiner = new StringJoiner(format);
        for (String value : input) {
            joiner.add(value);
        }
        return String.valueOf(joiner);
    }
}
