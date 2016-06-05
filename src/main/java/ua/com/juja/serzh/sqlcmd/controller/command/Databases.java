package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.Set;

/**
 * Created by serzh on 6/3/16.
 */
public class Databases extends Command {

    public Databases(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        Set<String> set = manager.getDatabases();
        if (set.size() > 0) {
            String bases = set.toString();
            String result = bases.substring(1, bases.length() - 1);
            view.write(String.format("Существующие databases: %s", result));
        } else {
            view.write("Databases отсутствуют");
        }
    }

    @Override
    public String commandFormat() {
        return "databases";
    }

    @Override
    public String description() {
        return "для получения списка баз";
    }
}
