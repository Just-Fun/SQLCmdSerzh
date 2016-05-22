package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class CreateDatabase extends CommandAbstract {

    public CreateDatabase(DatabaseManager manager, View view) {
        super(manager, view);
    }
    //    @Override
//    public boolean canProcess(String command) {
//        return command.startsWith("createDB|");
//    }

    @Override
    public void process(UserInput input) {
        input.validation(commandFormat());
        String databaseName = input.splitInput()[1];

        manager.createDatabase(databaseName);
        view.write(String.format("Database %s была успешно создана.", databaseName));
    }

    @Override // TODO может добавить в описание, что имя не может начинаться с цифры и еще чего-то нельзя...
    public String description() {
        return "для создания новой Database";
    }

    @Override
    public String commandFormat() {
        return "createDB|databaseName";
    }
}
