package sqlcmd.controller.command;

import sqlcmd.model.DatabaseManager;
import sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class DropDatabase implements Command {
    private DatabaseManager manager;
    private View view;

    public DropDatabase(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("drop|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length != 2) {
            throw new IllegalArgumentException("Формат команды 'drop|databaseName', а ты ввел: " + command);
        }
        manager.dropDatabase(data[1]);

        view.write(String.format("Database %s была успешно удалена.", data[1]));
    }

    @Override
    public String description() {
        return "для удаления Database";
    }

    @Override
    public String format() {
        return "drop|databaseName";
    }
}