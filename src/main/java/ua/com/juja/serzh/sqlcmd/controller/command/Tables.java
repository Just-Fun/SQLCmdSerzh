package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.Set;

/**
 * Created by serzh on 5/11/16.
 */
public class Tables extends Command {

    public Tables(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        Set<String> set = manager.getTableNames();
        if (set.size() > 0) {
            String tables = set.toString();
            String result = tables.substring(1, tables.length() - 1);
            view.write(String.format("Existing table: %s", result));
        } else {
            view.write("The tables are missing");
        }
    }

    @Override
    public String commandFormat() {
        return "tables";
    }

    @Override
    public String description() {
        return "for a list of all database tables, which are connected to the";
    }
}
