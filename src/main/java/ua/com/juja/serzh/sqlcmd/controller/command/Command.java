package ua.com.juja.serzh.sqlcmd.controller.command;

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

    public abstract void process(String input);

    public abstract String commandFormat();

    public abstract String description();

    public boolean canProcess(String input) {
        String[] parametersCommandFormat = splitInput(commandFormat());
        String[] parametersInput = splitInput(input);
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

    public void validationParameters(String input) {
        int formatLength = parametersLength(commandFormat());
        int inputLength = parametersLength(input);
        if (formatLength != inputLength) {
            throw new IllegalArgumentException(String.format("Формат команды '%s', а ты ввел: %s", commandFormat(), input));
        }
    }

    public void validationPairParameters(String input) {
        int inputLength = parametersLength(input);
        if (inputLength % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное количество параметров " +
                    "в формате '%s', а ты прислал: '%s'", commandFormat(), input));
        }
    }

    public void checkNameStartWithLetter(String input, String name) {
        char fistChar = input.charAt(0);
        if (!(fistChar >= 'a' && fistChar <= 'z') && !(fistChar >= 'A' && fistChar <= 'Z')) {
            throw new IllegalArgumentException(String.format(
                    "Имя %s должно начинаться с буквы, а у тебя начинается с '%s'", name, fistChar));
        }
    }

    public String[] splitInput(String input) {
        return input.split("\\|");
    }

    public int parametersLength(String input) {
        return input.split("\\|").length;
    }
}
