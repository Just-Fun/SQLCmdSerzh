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
//TODO: Иван :мне кажется этот метод проще сделать абстрактным, а проверять на валидность команду уже в самих классах командах.
//TODO Спросить еще пару мнений на этот счет.
    public boolean canProcess(UserInput command) {
        String[] parametersCommandFormat = commandFormat().split("\\|");
        String[] parametersInput = command.splitInput();
        return parametersInput[0].equals(parametersCommandFormat[0]);
    }

    public boolean dropConfirmation(String name) {
        view.write(String.format("Вы уверены, что хотите удалить %s? Y/N", name));
        if (view.read().toUpperCase().equals("Y")) {
            return true;
        }
        view.write("Действие отменено!");
        return false;
    }

    public abstract String commandFormat();

    public abstract String description();

    public abstract void process(UserInput userCommand);
}
