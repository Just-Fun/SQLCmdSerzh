package ua.com.juja.serzh.sqlcmd;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;
import ua.com.juja.serzh.sqlcmd.view.Console;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by serzh on 6/1/16.
 */
public class ForTests {
    public static void main(String[] args) {


        Set<String> bases = new LinkedHashSet<>(Arrays.asList("1st", "2nd"));
        String s = bases.toString().substring(1, bases.toString().length() - 1);
        System.out.println(s);

        /*Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter
        View view = new Console();
        DatabaseManager manager = new PostgresManager();
        manager.connect("postgres","postgres", "postgres");
        view.write(manager.getDatabases().toString());
        view.write(manager.getTableNames().toString());
        view.write(String.valueOf(manager.getTableSize("user33")));*/


        /*long timeBefore = System.currentTimeMillis();
        String s = "";
        StringBuffer buffer = new StringBuffer();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
//            s += "1";           // 8375
//            buffer.append("1"); //   20
//            builder.append("1"); // 19

//            s += String.format("%s1,", "3");          // 23385
//            buffer.append(String.format("%s1,", "3"));//  1434
//            builder.append(String.format("%s1,", "3")); // 1476
        }
        long timeAfter = System.currentTimeMillis();
        long time = timeAfter - timeBefore;
        System.out.println(time);
*/

     /*   try {
//            int i = 0 + 1;
//            throw new IOException();
        } catch (ArithmeticException e) {
            // nothing
        }*/

        /*
        * Here are the few most frequently seen unchecked exceptions –

NullPointerException
ArrayIndexOutOfBoundsException
ArithmeticException
IllegalArgumentException*/
        /*ivan.tsvelikov [2:27 PM]
        может у тебя в процессе тестов не все методы от этой базы дисконектятся. У тебя в dropDatabase есть вызов метода, сбрасывающий соединения, типа ps.executeQuery(String.format("SELECT pg_terminate_backend(pg_stat_activity.pid)\n" +
                "FROM pg_stat_activity\n" +
                "WHERE pg_stat_activity.datname = '%s'\n" +
                "  AND pid <> pg_backend_pid();",dbName));

        [2:27] Star this message
        Мне такое помогло*/
    }
}
