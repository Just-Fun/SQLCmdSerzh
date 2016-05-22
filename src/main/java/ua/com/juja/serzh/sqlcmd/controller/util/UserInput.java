package ua.com.juja.serzh.sqlcmd.controller.util;

/**
 * Created by serzh on 19.05.16.
 */
public class UserInput {
    String userCommand;

    public UserInput(String userCommand) {
        this.userCommand = userCommand;

    }
    //TODO  заменить метод на проверку четности аргументов, использоать для сложных методов
    public boolean validationCommandName(String commandFormat) {
        String nameCommand = commandFormat.split("\\|")[0];
        String inputCmmand = userCommand.split("\\|")[0];
        return nameCommand.equals(inputCmmand);
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
