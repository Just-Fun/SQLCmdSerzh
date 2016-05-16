package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class CreateTable implements Command {
    private DatabaseManager manager;
    private View view;

    public CreateTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("createTable|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Формат команды 'createTable|tableName', а ты ввел: " + command);
        }
        manager.createTable(data[1]);
        view.write(String.format("Таблица %s была успешно создана.", data[1]));
    }

    @Override
    public String description() {
        return "для создания новой таблицы, в круглых скобках вставить опиание колонок в SQL формате, пример:\n"
                + "\t\tcreateTable|user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))";
    }

    @Override
    public String format() {
        return "createTable|tableName(column1,column2,...,columnN)";
    }
}
