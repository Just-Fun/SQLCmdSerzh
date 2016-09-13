package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Clear extends Command {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        validationParameters(input);
        String tableName = input.split("\\|")[1];
        manager.clear(tableName);
        view.write(String.format("Table %s has been successfully cleared.", tableName));
    }

    @Override
    public String commandFormat() {
        return "clear|tableName";
    }

    @Override
    public String description() {
        return "to clean up the entire table";
    }
}
