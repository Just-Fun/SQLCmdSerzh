package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Connect extends Command {

    public Connect(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(String input) {
        validationParameters(input);
        String[] data = input.split("\\|");
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);
        view.write("Успех!");
    }

    @Override
    public String commandFormat() {
        return "connect|databaseName|userName|password";
    }

    @Override
    public String description() {
        return "для подключения к базе данных, с которой будем работать";
    }
}
