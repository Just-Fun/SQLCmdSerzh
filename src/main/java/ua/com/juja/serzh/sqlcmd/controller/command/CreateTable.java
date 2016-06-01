package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class CreateTable extends Command {

    public CreateTable(DatabaseManager manager, View view) {
        super(manager, view);
    }

    @Override
    public void process(UserInput input) {
        validationParameters(input);
        validationPresenceOfParentheses(input);
        String command = input.splitInput()[1];
        manager.createTable(command);

        String tableName = command.split("\\(")[0];
        view.write(String.format("Таблица %s была успешно создана.", tableName));
        Command find = new Find(manager, view);
        find.process(new UserInput("find|" + tableName));
    }

    @Override
    public String commandFormat() {
        return "createTable|tableName(column1,column2,...,columnN)";
    }

    @Override
    public String description() {
        return "для создания новой таблицы, в круглых скобках вставить опиание колонок в SQL формате, пример:\n"
                + "\t\tcreateTable|user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))";
    }

    public void validationPresenceOfParentheses(UserInput input) {
        int presenceOfParentheses = (input.toString().split("\\(")).length;
        if (presenceOfParentheses < 2) {
            throw new IllegalArgumentException(String.format("Формат команды 'createTable|tableName" +
                    "(column1,column2,...,columnN)' в SQL!!! формате, а ты ввел: %s", input.toString()));
        }
    }
}
