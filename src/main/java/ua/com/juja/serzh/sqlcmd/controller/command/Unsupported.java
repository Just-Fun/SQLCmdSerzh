package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Unsupported extends Command {

    public Unsupported(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String input) {
        view.write("Nonexistent command: " + input);
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
