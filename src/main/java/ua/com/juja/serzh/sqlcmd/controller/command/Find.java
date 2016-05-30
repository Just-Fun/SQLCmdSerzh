package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class Find extends Command {

    public Find(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(UserInput input) {
        input.validationParameters(commandFormat());

        String tableName = input.splitInput()[1];
        Set<String> tableColumns = manager.getTableColumns(tableName);
        List<Map<String, Object>> tableData = manager.getTableData(tableName);
        TableConstructor constructor = new TableConstructor(tableColumns, tableData);

        view.write(constructor.getTableString());
    }

    @Override
    public String commandFormat() {
        return "find|tableName";
    }

    @Override
    public String description() {
        return "для получения содержимого таблицы 'tableName'";
    }
}
