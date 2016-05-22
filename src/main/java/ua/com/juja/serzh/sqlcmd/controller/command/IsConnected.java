package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
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
    public boolean canProcess(UserInput command) {
        return !manager.isConnected();
    }

    @Override
    public void process(UserInput input) {
        view.write(String.format("Вы не можете пользоваться командой '%s' пока не подключитесь " +
                "с помощью комманды connect|databaseName|userName|password", input.toString()));
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
