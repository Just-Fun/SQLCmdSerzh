package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Exit extends Command {

    public Exit(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String command) {
        view.write("See you soon!");
        throw new ExitException();
    }

    @Override
    public String description() {
        return "to exit from the program";
    }

    @Override
    public String commandFormat() {
        return "exit";
    }
}
