package sqlcmd;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sqlcmd.controller.MainController;
import sqlcmd.model.DatabaseManager;
import sqlcmd.model.JDBCDatabaseManager;
import sqlcmd.view.Console;
import sqlcmd.view.View;

/**
 * Created by indigo on 25.08.2015.
 */
public class Main {

    public static void main(String[] args) {
        // Стащил у Жени, надо спросить что это, без этих строк выкидывало какую-то...
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF); //Disable log4j from text table formatter

        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    } // connect|sqlcmd|postgres|postgres
}
