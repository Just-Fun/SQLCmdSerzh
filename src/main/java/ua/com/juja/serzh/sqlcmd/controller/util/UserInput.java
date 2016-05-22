package ua.com.juja.serzh.sqlcmd.controller.util;

import ua.com.juja.serzh.sqlcmd.controller.command.Command;

/**
 * Created by serzh on 19.05.16.
 */
public class UserInput {
    String userCommand;

    public UserInput(String userCommand) {
        this.userCommand = userCommand;

    }

    public void validationParametersLength(String commandFormat) {
        int formatLength = (commandFormat.split("\\|")).length;
        if (formatLength != parametersLength()) {
            throw new IllegalArgumentException(String.format("Формат команды '%s', а ты ввел: %s", commandFormat, userCommand));
        }
    }

    public void validationPairParameters(UserInput input, Command command) {
        int length = getParametersLength(input);
        if (length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное количество параметров " +
                    "в формате '%s', а ты прислал: '%s'",command.commandFormat(), input.toString()));
        }
    }

    private int getParametersLength(UserInput input) {
        String[] data = input.toString().split("\\|");
        return data.length;
    }

    private int parametersLength() {
        return (userCommand.split("\\|")).length;
    }

    public String[] splitInput() {
        return userCommand.split("\\|");
    }

    @Override
    public String toString() {
        return userCommand;
    }
}
