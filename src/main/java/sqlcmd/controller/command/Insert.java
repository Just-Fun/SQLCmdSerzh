package sqlcmd.controller.command;

import sqlcmd.model.DataSet;
import sqlcmd.model.DatabaseManager;
import sqlcmd.view.View;

/**
 * Created by indigo on 28.08.2015.
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

        DataSet dataSet = new DataSet();
        for (int index = 1; index < (data.length / 2); index++) {
            String columnName = data[index*2];
            String value = data[index*2 + 1];

            dataSet.put(columnName, value);
        }
        manager.insert(tableName, dataSet);

        view.write(String.format("Запись %s была успешно создана в таблице '%s'.", dataSet, tableName));
    }

    @Override
    public String description() {
        return "для создания записи в таблице";
    }

    @Override
    public String format() {
        return "insert|tableName|column1|value1|column2|value2|...|columnN|valueN";
    }
}
