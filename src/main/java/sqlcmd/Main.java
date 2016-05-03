package sqlcmd;

import sqlcmd.controller.MainController;
import sqlcmd.model.DatabaseManager;
import sqlcmd.model.JDBCDatabaseManager;
import sqlcmd.view.Console;
import sqlcmd.view.View;

/**
 * Created by indigo on 25.08.2015.
 */
public class Main {     // connect|sqlcmd|postgres|postgres

    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();
    } // connect|sqlcmd|postgres|postgres
}
