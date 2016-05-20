package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DataSet;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class Insert implements Command {
    private final DatabaseManager manager;
    private final View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Должно быть четное " +
                    "количество параметров в формате " +
                    "'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', " +
                    "а ты прислал: '%s'", command));
        }
        String tableName = data[1];
// TODO убрать дублирование, а может выпилить вообще везде этот DataSet, подумать
        Map<String, Object> map = new LinkedHashMap<>();
        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            dataSet.put(columnName, value);
            map.put(columnName, value);
        }
        manager.insert(tableName, dataSet);

        List<Map<String, Object>> tableData = new LinkedList<>();
        tableData.add(map);
        TableConstructor constructor = new TableConstructor(dataSet.getNames(), tableData);
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
}
