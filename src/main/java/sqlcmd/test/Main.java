package sqlcmd.test;

import sqlcmd.model.DataSet;

import java.util.Arrays;
import java.util.Locale;

/**
 * Created by serzh on 24.04.16.
 */
public class Main {
    public static void main(String[] args) {
        /*DataSet set = new DataSet();
        set.put("Vasia", 18);
        set.put("Misha", 16);
        System.out.println(set.toString());
        System.out.println(set.get("Vasia"));
        System.out.println(Arrays.toString(set.getNames()));
        System.out.println(Arrays.toString(set.getValues()));*/

        String s = "Vasia123";
        s = s.format("%s", s);
        s += s.format("%s", s);
        s += s.format("%s", s);
//        s += s;
        System.out.println(s);




    }
}
