package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/12/16.
 */
public class DropTable extends Command {

    public DropTable(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        validationParameters(input);
        String tableName = splitInput(input)[1];

        if (!dropConfirmation(tableName)) return;
        manager.dropTable(tableName);
        view.write(String.format("Table %s has been successfully removed.", tableName));
    }

    @Override
    public String commandFormat() {
        return "dropTable|tableName";
    }

    @Override
    public String description() {
        return "to delete the table";
    }
}
