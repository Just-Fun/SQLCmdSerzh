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
        view.write(String.format("Are you sure you want to delete %s? Y/N", name));
        if (view.read().toUpperCase().equals("Y")) {
            return true;
        }
        view.write("Action canceled!");
        return false;
    }

    public void validationParameters(String input) {
        int formatLength = parametersLength(commandFormat());
        int inputLength = parametersLength(input);
        if (formatLength != inputLength) {
            throw new IllegalArgumentException(String.format("The command format is '%s', but you typed: %s", commandFormat(), input));
        }
    }

    public void validationPairParameters(String input) {
        int inputLength = parametersLength(input);
        if (inputLength % 2 != 0) {
            throw new IllegalArgumentException(String.format("Must be an even number of parameters " +
                    "in a format '%s', but you typed: '%s'", commandFormat(), input));
        }
    }

    public void checkNameStartWithLetter(String input, String name) {
        char fistChar = input.charAt(0);
        if (!(fistChar >= 'a' && fistChar <= 'z') && !(fistChar >= 'A' && fistChar <= 'Z')) {
            throw new IllegalArgumentException(String.format(
                    "%s name must start with a letter, and you start with '%s'", name, fistChar));
        }
    }

    public String[] splitInput(String input) {
        return input.split("\\|");
    }

    public int parametersLength(String input) {
        return input.split("\\|").length;
    }
}
