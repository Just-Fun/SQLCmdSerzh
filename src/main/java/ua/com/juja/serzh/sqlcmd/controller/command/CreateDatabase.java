package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class CreateDatabase extends Command {

    public CreateDatabase(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(UserInput input) {
        input.validationParameters(commandFormat());
        String databaseName = input.splitInput()[1];

        manager.createDatabase(databaseName);
        view.write(String.format("Database %s была успешно создана.", databaseName));
    }

    @Override
    public String commandFormat() {
        return "createDB|databaseName";
    }

    @Override
    public String description() {
        return "для создания новой Database. Имя базы должно начинаться с буквы, прописные буквы становяться строчными.";
    }
}
