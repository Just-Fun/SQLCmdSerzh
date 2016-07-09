package ua.com.juja.serzh.sqlcmd;

import java.util.*;

/**
 * Created by serzh on 6/9/16.
 */
public class ForTests {
    public static void main(String[] args) {

     /*   List<String> input = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            input.add(String.valueOf(i));
        }*/

        Map<Integer, String> integerMap = new LinkedHashMap<>();
        for (int i = 0; i < 4000000; i++) {
            integerMap.put(i, "s" + i);
        }
        StringJoiner joiner = new StringJoiner(",");

        fori(integerMap, joiner);
//        lambda(integerMap, joiner);

        // на 1.000 -> Fori: 6, Lambda: 231
        // на 10.000 -> Fori: 16, Lambda: 250
        // на 100.000 -> Fori: 53, Lambda: 300
        // на 1.000.000 -> Fori: 100, Lambda: 307
        // на 4.000.000 -> Fori: 500, Lambda: 1000

    }

    private static void lambda(Map<Integer, String> integerMap, StringJoiner joiner) {
        long start2 = System.currentTimeMillis();

        integerMap.entrySet().stream().forEach(x -> joiner.add(x.getValue()));
//        return joiner.toString();
//        String.valueOf(joiner);
        long finish2 = System.currentTimeMillis();
        long time2 = finish2 - start2;
        System.out.println("Lambda: " + time2);
    }

    private static void fori(Map<Integer, String> integerMap, StringJoiner joiner) {
        long start = System.currentTimeMillis();
        for (Map.Entry<Integer, String> entry : integerMap.entrySet()) {
            joiner.add(entry.getValue());
        }
//        joiner.toString();
//        return String.valueOf(joiner);
        long finish = System.currentTimeMillis();
        long time = finish - start;
        System.out.println("Fori: " + time);
    }


    private static String getFormatted(List<String> input, String format) {
        StringBuilder values = new StringBuilder("");
        for (String value : input) {
            values.append(String.format(format, value));
        }
        return values.substring(0, values.length() - 1);
    }

    private static String getJoiner2(List<String> input, StringJoiner format) {
        for (String value : input) {
            format.add(value);
        }
        return String.valueOf(format);
    }

    private static String getJoiner(List<String> input, String format) {
        StringJoiner joiner = new StringJoiner(format);
        for (String value : input) {
            joiner.add(value);
        }
        return String.valueOf(joiner);
    }
}
