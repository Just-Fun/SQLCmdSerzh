package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;

/**
 * Created by serzh on 5/11/16.
 */
public interface Command {

    boolean canProcess(String command);

    void process(UserInput command);

    String description();

    String commandFormat();
}
