package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.TableConstructor;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.*;

/**
 * Created by serzh on 5/11/16.
 */
public class InsertSimple extends Command {

    public InsertSimple(DatabaseManager manager, View view) {
        super(manager, view);
    }

    Set<String> columns;
    private boolean exitMain;

    @Override
    public void process(String input) {
        exitMain = false;
        String[] data = splitInput(input);
        validationParameters(input);
        String tableName = data[1];
        columns = manager.getTableColumns(tableName);
        Map<String, Object> command = createQuery();
        if (exitMain) {
            view.write("Выход в основное меню");
        } else {
            manager.insert(tableName, command);
            view.write(String.format("В таблице '%s' была успешно добавлена запись:", tableName));
            view.write(getTableConstructor(command));
        }
    }

    private Map<String, Object> createQuery() {
        Map<String, Object> data = new LinkedHashMap<>();
        for (String column : columns) {
            Object value = setValue(column);
            if (value.equals("0")) {
                exitMain = true;
                return data;
            } else {
                data.put(column, value);
            }
        }
        return data;
    }

    private Object setValue(String column) {
        boolean exit = false;
        Object input = "";
        while (!exit) {
            view.write(String.format("Введите значение в поле '%s' или введите '0' для выхода в основное меню.", column));
            input = view.read();
            if (input.equals("")) {
                view.write("Нужно ввести имя для колонки PRIMARY KEY, а вы вели пустую строку");
            } else {
                exit = true;
            }
        }
        return input;
    }

    public String getTableConstructor(Map<String, Object> dataSet) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.putAll(dataSet);

        List<Map<String, Object>> tableData = new LinkedList<>();
        tableData.add(map);
        TableConstructor constructor = new TableConstructor(dataSet.keySet(), tableData);
        return constructor.getTableString();
    }

    @Override
    public String commandFormat() {
        return "insertTable|tableName";
    }

    @Override
    public String description() {
        return "для пошагового создания записи в существующей таблице";
    }

}
