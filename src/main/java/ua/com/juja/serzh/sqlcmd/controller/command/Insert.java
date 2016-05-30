package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
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
    public void process(UserInput input) {
        String[] data = input.splitInput();
        input.validationPairParameters(input, this);
        String tableName = data[1];
        Map<String, Object> command = getDataSet(data);

        manager.insert(tableName, command);

        view.write(String.format("В таблице '%s' была успешно добавлена запись:", tableName));
        TableConstructor constructor = input.getTableConstructorFromDataSet(command);
        view.write(constructor.getTableString());
    }

    @Override
    public String commandFormat() {
        return "insert|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }

    @Override
    public String description() {
        return "для создания записи в существующей таблице";
    }

    private Map<String, Object> getDataSet(String[] data) {
//        DataSet dataSet = new DataSet();
        Map<String, Object> data1 = new LinkedHashMap<>();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
//            dataSet.put(columnName, value);
            data1.put(columnName, value);
        }
        return data1;
    }


}
