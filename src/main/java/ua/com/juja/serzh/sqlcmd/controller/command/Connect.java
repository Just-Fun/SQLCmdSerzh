package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Connect extends CommandAbstract {

    private final static String COMMAND_SAMPLE = "connect|databaseName|userName|password";

    public Connect(DatabaseManager manager, View view) {
        super(manager, view);
    }

    //    @Override
//    public boolean canProcess(String command) {
//        return command.startsWith("connect|");
//    }

    @Override
    public void process(UserInput input) {
        input.validation(commandFormat());
        String[] data = input.splitInput();
        String databaseName = data[1];
        String userName = data[2];
        String password = data[3];

        manager.connect(databaseName, userName, password);
        view.write("Успех!");
    }

    @Override
    public String description() {
        return "для подключения к базе данных, с которой будем работать";
    }

    @Override
    public String commandFormat() {
        return COMMAND_SAMPLE;
    }
}
