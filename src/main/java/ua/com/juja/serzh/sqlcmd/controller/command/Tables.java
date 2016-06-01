package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
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
        Set<String> tableNames = manager.getTableNames();
        view.write(tableNames.toString());
    }

    @Override
    public String commandFormat() {
        return "tables";
    }

    @Override
    public String description() {
        return "для получения списка всех таблиц базы, к которой подключились";
    }
}
