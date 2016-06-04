package ua.com.juja.serzh.sqlcmd;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;
import ua.com.juja.serzh.sqlcmd.view.Console;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 6/1/16.
 */
public class ForTests {
    public static void main(String[] args) {
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter
        View view = new Console();
        DatabaseManager manager = new PostgresManager();
        manager.connect("postgres","postgres", "postgres");
        view.write(manager.getDatabases().toString());
        view.write(manager.getTableNames().toString());
        view.write(String.valueOf(manager.getTableSize("user33")));


      /*  long timeBefore = System.currentTimeMillis();
//        String s = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 100000; i++) {
//            s += "1";           // 8375
//            buffer.append("1"); //   18

//            s += String.format("%s1,", "3");          // 24203
//            buffer.append(String.format("%s1,", "3"));//  1391
        }
        long timeAfter = System.currentTimeMillis();
        long time = timeAfter - timeBefore;
        System.out.println(time);*/


    }
}
