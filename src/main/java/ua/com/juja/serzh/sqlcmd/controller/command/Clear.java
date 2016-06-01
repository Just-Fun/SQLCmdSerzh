package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Clear extends Command {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(UserInput input) {
        validationParameters(input);
        String tableName = input.splitInput()[1];
        manager.clear(tableName);

        view.write(String.format("Таблица %s была успешно очищена.", tableName));
    }

    @Override
    public String commandFormat() {
        return "clear|tableName";
    }

    @Override
    public String description() {
        return "для очистки всей таблицы";
    }
}
