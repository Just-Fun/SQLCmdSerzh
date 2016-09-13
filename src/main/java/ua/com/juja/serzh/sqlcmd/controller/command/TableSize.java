package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class TableSize extends Command {

    public TableSize(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        validationParameters(input);
        String tableName = input.split("\\|")[1];
        int size = manager.getTableSize(tableName);
        view.write(String.format("The number of rows in the table '%s': %s.", tableName, size));
    }

    @Override
    public String commandFormat() {
        return "size|tableName";
    }

    @Override
    public String description() {
        return "The number of rows in the table";
    }
}
