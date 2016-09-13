package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class Insert extends Command {

    public Insert(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        String[] data = splitInput(input);
        validationPairParameters(input);
        String tableName = data[1];
        Map<String, Object> command = getDataSet(data);

        manager.insert(tableName, command);
        view.write(String.format("The table '%s' has been successfully added record:", tableName));
        view.write(getTableConstructor(command));
    }

    @Override
    public String commandFormat() {
        return "insert|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }

    @Override
    public String description() {
        return "to create a record in the existing table";
    }

    private Map<String, Object> getDataSet(String[] data) {
        Map<String, Object> data1 = new LinkedHashMap<>();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            data1.put(columnName, value);
        }
        return data1;
    }

    public String getTableConstructor(Map<String, Object> dataSet) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.putAll(dataSet);

        List<Map<String, Object>> tableData = new LinkedList<>();
        tableData.add(map);
        TableConstructor constructor = new TableConstructor(dataSet.keySet(), tableData);
        return constructor.getTableString();
    }
}
