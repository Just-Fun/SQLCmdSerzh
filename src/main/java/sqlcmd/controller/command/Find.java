package sqlcmd.controller.command;

import sqlcmd.model.DataSet;
import sqlcmd.model.DatabaseManager;
import sqlcmd.model.TableConstructor;
import sqlcmd.view.View;

import java.util.*;

/**
 * Created by indigo on 28.08.2015.
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
        String tableName = data[1]; // TODO to add validation


        Set<String> tableColumns = manager.getTableColumns(tableName);
//        printHeader(tableColumns);

        java.util.List<Map<String, Object>> tableData = manager.getTableData(tableName);
//        printTable(tableData);
        TableConstructor constructor = new TableConstructor(tableColumns, tableData);
        view.write(constructor.getTableString());
    }

    @Override
    public String description() {
        return "для получения содержимого таблицы 'tableName'";
    }

    @Override
    public String format() {
        return "find|tableName";
    }
}
