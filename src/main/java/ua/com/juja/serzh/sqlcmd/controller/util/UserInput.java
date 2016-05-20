package ua.com.juja.serzh.sqlcmd.controller.util;

/**
 * Created by serzh on 19.05.16.
 */
public class UserInput {
    String userCommand;

    public UserInput(String userCommand) {
        this.userCommand = userCommand;

    }

    public void validation(String commandFormat) {
        int formatLength = (commandFormat.split("\\|")).length;
        if (formatLength != userCommandLength()) {
            throw new IllegalArgumentException(String.format("Формат команды '%s', а ты ввел: %s", commandFormat, userCommand));
        }
    }

    private int userCommandLength() {
        return (userCommand.split("\\|")).length;
    }

    public String[] splitInput() { return userCommand.split("\\|"); }

    @Override
    public String toString() {
        return userCommand;
    }
}
