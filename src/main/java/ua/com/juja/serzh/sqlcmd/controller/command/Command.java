package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 22.05.16.
 */
public abstract class Command {

    protected DatabaseManager manager;
    protected View view;

    public Command(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    public Command(View view) {
        this.view = view;
    }

    public Command() {
    }

    public boolean canProcess(UserInput command) {
        String[] parametersCommandFormat = commandFormat().split("\\|");
        String[] parametersInput = command.splitInput();
        return parametersInput[0].toLowerCase().equals(parametersCommandFormat[0].toLowerCase());
    }

    public boolean dropConfirmation(String name) {
        view.write(String.format("Вы уверены, что хотите удалить %s? Y/N", name));
        if (view.read().toUpperCase().equals("Y")) {
            return true;
        }
        view.write("Действие отменено!");
        return false;
    }

    public void validationParameters(UserInput input) {
        int formatLength = (commandFormat().split("\\|")).length;
        if (formatLength != input.parametersLength()) {
            throw new IllegalArgumentException(String.format("Формат команды '%s', а ты ввел: %s", commandFormat(), input));
        }
    }

    public void validationPairParameters(UserInput input) {
        if (input.parametersLength() % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное количество параметров " +
                    "в формате '%s', а ты прислал: '%s'", commandFormat(), input.toString()));
        }
    }

    public abstract String commandFormat();

    public abstract String description();

    public abstract void process(UserInput userCommand);
}
