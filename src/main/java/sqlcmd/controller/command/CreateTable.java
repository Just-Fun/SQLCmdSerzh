package sqlcmd.controller.command;

import sqlcmd.model.DatabaseManager;
import sqlcmd.view.View;

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
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Формат команды 'create|tableName', а ты ввел: " + command);
        }
        manager.createTable(data[1]);

        view.write(String.format("Таблица %s была успешно создана.", data[1]));
    }

    @Override
    public String description() {
        return "для создания новой таблицы";
    }

    @Override
    public String format() {
        return "create|tableName";
    }
}
