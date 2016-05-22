package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class DropDatabase extends Command {

    public DropDatabase(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(UserInput input) {
        input.validation(commandFormat());
        String databaseName = input.splitInput()[1];
        manager.dropDatabase(databaseName);

        view.write(String.format("Database %s была успешно удалена.", databaseName));
    }

    @Override
    public String description() {
        return "для удаления Database";
    }

    @Override
    public String commandFormat() {
        return "dropDB|databaseName";
    }
}
