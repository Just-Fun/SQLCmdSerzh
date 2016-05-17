package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class Find implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];
        Set<String> tableColumns = manager.getTableColumns(tableName);

        List<Map<String, Object>> tableData = manager.getTableData(tableName);
        TableConstructor constructor = new TableConstructor(tableColumns, tableData);
        view.write(constructor.getTableString());
    }

    @Override
    public String description() {
        return "для получения содержимого таблицы 'tableName'";
    }

    @Override
    public String commandFormat() {
        return "find|tableName";
    }
}
