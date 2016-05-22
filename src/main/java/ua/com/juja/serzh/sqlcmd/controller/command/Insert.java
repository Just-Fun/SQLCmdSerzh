package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DataSet;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class Insert extends CommandAbstract {

    public Insert(DatabaseManager manager, View view) {
        super(manager, view);
    }
    //    @Override
//    public boolean canProcess(String command) {
//        return command.startsWith("insert|");
//    }

    @Override
    public void process(UserInput input) {
        String[] data = input.splitInput();
        validation(input, data);
        String tableName = data[1];
        DataSet command = getDataSet(data);

        manager.insert(tableName, command);

        TableConstructor constructor = getTableConstructor(command);
        view.write(String.format("В таблице '%s' была успешно добавлена запись:", tableName));
        view.write(constructor.getTableString());
    }

    @Override
    public String description() {
        return "для создания записи в существующей таблице";
    }

    @Override
    public String commandFormat() {
        return "insert|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }

    private DataSet getDataSet(String[] data) {
        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            dataSet.put(columnName, value);
        }
        return dataSet;
    }

    private TableConstructor getTableConstructor(DataSet dataSet) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.putAll(dataSet.returnData());

        List<Map<String, Object>> tableData = new LinkedList<>();
        tableData.add(map);
        return new TableConstructor(dataSet.getNames(), tableData);
    }

    private void validation(UserInput input, String[] data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное количество параметров" +
                    " в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "а ты прислал: '%s'", input.toString()));
        }
    }
}
