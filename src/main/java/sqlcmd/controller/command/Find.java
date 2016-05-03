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

    /*private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("--------------------");
        view.write(result);
        view.write("--------------------");
    }

    private void printTable(DataSet[] tableData) {
        for (DataSet row : tableData) {
            printRow(row);
        }
        view.write("--------------------");
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);
    }*/
    }
