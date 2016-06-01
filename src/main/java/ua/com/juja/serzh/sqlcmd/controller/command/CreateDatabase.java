package ua.com.juja.serzh.sqlcmd.controller.command;

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
    public void process(String input) {
        validationParameters(input);
        checkDBNameStartWithLetter(input);
        String databaseName = input.split("\\|")[1];

        manager.createDatabase(databaseName);
        view.write(String.format("Database %s была успешно создана.", databaseName));
    }

    @Override
    public String commandFormat() {
        return "createDB|databaseName";
    }

    @Override
    public String description() {
        return "для создания новой Database. Имя базы должно начинаться с буквы.";
    }

    public void checkDBNameStartWithLetter(String input) {
        String databaseName = input.split("\\|")[1];
        char fistChar = databaseName.charAt(0);
        //TODO написать тест
        if (!(fistChar >= 'a' && fistChar <= 'z') && !(fistChar >= 'A' && fistChar <= 'Z')) {
            throw new IllegalArgumentException(String.format(
                    "Имя базы должно начинаться с буквы, а у тебя начинается с '%s'", fistChar));
        }

    }
}
