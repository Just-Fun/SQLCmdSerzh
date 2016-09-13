package ua.com.juja.serzh.sqlcmd.controller.command;

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
    public void process(String input) {
        validationParameters(input);
        validationPresenceOfParentheses(input);
        String command = splitInput(input)[1];
        String tableName = command.split("\\(")[0];
        checkNameStartWithLetter(tableName, "Table");

        manager.createTable(command);
        view.write(String.format("Table %s was successfully created.", tableName));
        Command find = new Find(manager, view);
        find.process("find|" + tableName);
    }

    @Override
    public String commandFormat() {
        return "createTableSQL|tableName(column1,column2,...,columnN)";
    }

    @Override
    public String description() {
        return "to create a new table for those who know SQL, parentheses opianie insert columns in SQL format example:\n"
                + "\t\tcreateTableSQL|user1(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))";
    }

    public void validationPresenceOfParentheses(String input) {
        int presenceOfParentheses = (input.split("\\(")).length;
        if (presenceOfParentheses < 2) {
            throw new IllegalArgumentException(String.format("The command format is '" + commandFormat() + "' in SQL !!! format, but you typed: %s", input));
        }
    }
}
