package ua.com.juja.serzh.sqlcmd;

/**
 * Created by serzh on 6/1/16.
 */
public class ForTests {
    public static void main(String[] args) {
        String s = String.format("%s,", "rt");

        s += String.format("%s,", "io");
        s = s.substring(0, s.length() - 1);
//        System.out.println(s);

        StringBuffer stringBuffer = new StringBuffer("e");
        stringBuffer.append("t");
        stringBuffer.append(String.format("%s,", "io"));
//        stringBuffer.substring(0, stringBuffer.length() - 2);
        String s1 = stringBuffer.substring(0, stringBuffer.length() - 1);

        System.out.println(s1);
    }
}
