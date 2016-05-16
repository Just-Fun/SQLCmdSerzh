package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/12/16.
 */
public class DropTable implements Command {
    private DatabaseManager manager;
    private View view;

    public DropTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("dropTable|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Формат команды 'create|dropTable', а ты ввел: " + command);
        }
        manager.dropTable(data[1]);

        view.write(String.format("Таблица %s была успешно удалена.", data[1]));
    }

    @Override
    public String description() {
        return "для удаления таблицы";
    }

    @Override
    public String commandFormat() {
        return "dropTable|tableName";
    }
}
