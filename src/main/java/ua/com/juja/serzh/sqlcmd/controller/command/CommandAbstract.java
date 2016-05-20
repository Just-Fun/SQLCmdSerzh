package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;

/**
 * Created by serzh on 5/20/16.
 */
public class CommandAbstract {

    public boolean canProcess1(UserInput input) {
        return input.validationCommandName(commandFormat());
    }

    public String commandFormat(){
     return null;
    }
}
