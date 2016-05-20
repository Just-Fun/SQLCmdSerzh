package ua.com.juja.serzh.sqlcmd.controller.command;

/**
 * Created by serzh on 19.05.16.
 */
public class UserCommand {
    String userCommand;

    public UserCommand(String userCommand) {
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
}
