package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Clear extends CommandAbstract {

    public Clear(DatabaseManager manager, View view) {
        super(manager, view);
    }

    //    @Override
//    public boolean canProcess(String command) {
//        return command.split("\\|")[0].equals("clear");
//    }


    @Override
    public void process(UserInput input) {
        input.validation(commandFormat());
        String tableName = input.splitInput()[1];
        manager.clear(tableName);

        view.write(String.format("Таблица %s была успешно очищена.", tableName));
    }

    @Override
    public String description() {
        return "для очистки всей таблицы";
    }

    @Override
    public String commandFormat() {
        return "clear|tableName";
    }
}
