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
        manager.connect("sqlcmd5database","postgres", "postgres");

        view.write(manager.getTableNames().toString());
        view.write(manager.getDatabases().toString());

    }
}
