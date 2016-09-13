package ua.com.juja.serzh.sqlcmd.controller.command;

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
    public void process(String input) {
        validationParameters(input);
        String databaseName = splitInput(input)[1];
        if (!dropConfirmation(databaseName)) return;

        String databaseNameConnect = manager.getDatabaseName();
        if (databaseName.equals(databaseNameConnect)) {
            view.write("You can not delete the base to which you are connected.");
            view.write(String.format("To delete the current database '%s', connect to another database.", databaseName));
            return;
        }
        // implementation the removal of the base, to which it is connected
        /*if (databaseName.equals(databaseNameConnect)) {
            manager.connect("", Connect.userName,Connect.password);
            view.write(String.format("You are disconnected from the database '%s'.", databaseNameConnect));
            manager.dropDatabase(databaseName);
            view.write(String.format("Database %s has been successfully removed.", databaseName));
            return;
        }*/
        manager.dropDatabase(databaseName);
        view.write(String.format("Database '%s' has been successfully removed.", databaseName));
    }

    @Override
    public String commandFormat() {
        return "dropDB|databaseName";
    }

    @Override
    public String description() {
        return "to remove the database. The base must be free of any konekshina.";
    }
}
