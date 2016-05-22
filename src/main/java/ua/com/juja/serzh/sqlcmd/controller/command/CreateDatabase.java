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
        input.validation(commandFormat());
        String databaseName = input.splitInput()[1];

        manager.createDatabase(databaseName);
        view.write(String.format("Database %s была успешно создана.", databaseName));
    }

    // TODO может добавить в описание, что имя не может начинаться с цифры, заглавные буквы становяться прописными и еще чего-то нельзя...
    @Override
    public String description() {
        return "для создания новой Database";
    }

    @Override
    public String commandFormat() {
        return "createDB|databaseName";
    }
}
