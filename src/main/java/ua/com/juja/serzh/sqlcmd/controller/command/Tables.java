package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.Set;

/**
 * Created by serzh on 5/11/16.
 */
public class Tables extends CommandAbstract {

    public Tables(DatabaseManager manager, View view) {
        super(manager, view);
    }

    //    @Override
//    public boolean canProcess(String command) {
//        return command.equals("tables");
//    }

    @Override
    public void process(UserInput input) {
        Set<String> tableNames = manager.getTableNames();
        view.write(tableNames.toString());
    }

    @Override
    public String description() {
        return "для получения списка всех таблиц базы, к которой подключились";
    }

    @Override
    public String commandFormat() {
        return "tables";
    }
}
