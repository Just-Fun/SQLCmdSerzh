package ua.com.juja.serzh.sqlcmd.controller.command;

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
    public void process(String input) {
        validationParameters(input);

        String tableName = splitInput(input)[1];
        Set<String> tableColumns = manager.getTableColumns(tableName);

        if (tableColumns.size() > 0) {
        List<Map<String, Object>> tableData = manager.getTableData(tableName);
        TableConstructor constructor = new TableConstructor(tableColumns, tableData);
        view.write(constructor.getTableString());
        } else {
            view.write(String.format("The data in the table '%s' are absent", tableName));
        }
    }

    @Override
    public String commandFormat() {
        return "find|tableName";
    }

    @Override
    public String description() {
        return "to obtain the contents of the table 'tableName'";
    }
}
