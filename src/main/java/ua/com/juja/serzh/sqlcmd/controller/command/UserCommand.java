package ua.com.juja.serzh.sqlcmd.controller.command;

/**
 * Created by serzh on 19.05.16.
 */
public class UserCommand {
    String userCommand;
    String commandFormat;
    Command command;

    public UserCommand(String userCommand, Command command) {
        this.userCommand = userCommand;
        this.command = command;
    }

    public void validation(String userCommand, int num) {
        String[] data = userCommand.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException(String.format("Формат команды '%s', а ты ввел: %s", command.commandFormat(), command));
        }
    }
}
