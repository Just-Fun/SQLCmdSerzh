package ua.com.juja.serzh.sqlcmd;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ua.com.juja.serzh.sqlcmd.controller.MainController;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;
import ua.com.juja.serzh.sqlcmd.view.Console;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Main {

    public static void main(String[] args) {
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter

        View view = new Console();
        DatabaseManager manager = new PostgresManager();
        MainController controller = new MainController(view, manager);
        controller.run();

    }
    // connect|postgres|postgres|postgres
}
