package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class IsConnected extends Command {

    public IsConnected(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String input) {
        view.write("You can not use the commands until you connect " +
                "using command connect|databaseName|userName|password");
    }

    @Override
    public String commandFormat() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }
}
