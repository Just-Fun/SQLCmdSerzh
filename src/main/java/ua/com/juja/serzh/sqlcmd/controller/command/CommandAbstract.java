package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 22.05.16.
 */
public abstract class CommandAbstract {

    protected DatabaseManager manager;
    protected View view;

    public CommandAbstract(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    public CommandAbstract(View view) {
        this.view = view;
    }

    public CommandAbstract() {
    }

    public boolean canProcess(UserInput command) {
        String[] splitCommandFormat = commandFormat().split("\\|");
        String[] parameters = command.splitInput();
        return parameters[0].equals(splitCommandFormat[0]);
    }

    public abstract String description();

    public abstract String commandFormat();

    public abstract void process(UserInput userCommand);
}