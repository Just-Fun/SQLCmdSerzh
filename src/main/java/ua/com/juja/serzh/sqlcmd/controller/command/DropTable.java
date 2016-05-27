package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/12/16.
 */
public class DropTable extends Command {

    public DropTable(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(UserInput input) {
        input.validationParametersLength(commandFormat());
        String tableName = input.splitInput()[1];

        if (!dropConfirmation(tableName)) return;
        manager.dropTable(tableName);
        view.write(String.format("Таблица %s была успешно удалена.", tableName));
    }

    @Override
    public String commandFormat() {
        return "dropTable|tableName";
    }

    @Override
    public String description() {
        return "для удаления таблицы";
    }
}
