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
        Set<String> bases = manager.getDatabases();
        view.write(bases.toString());
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
