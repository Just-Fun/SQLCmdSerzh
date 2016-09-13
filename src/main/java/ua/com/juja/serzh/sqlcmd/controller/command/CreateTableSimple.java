package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 6/6/16.
 */
public class CreateTableSimple extends Command {

    public CreateTableSimple(DatabaseManager manager, View view) {
            super(manager, view);
    }

    String orExit = " or '0' to exit to the main menu";
    String fromLetter = "(name must start with a letter)";
    private boolean exitMain;
    String query = "";

    @Override
    public void process(String input) {
        exitMain = false;
        query = "";
        validationParameters(input);
        createQuery();

        if (!exitMain) {
            manager.createTable(query);
            String table = query.split("\\(")[0];
            view.write(String.format("Table %s was successfully created.", table));
            Command find = new Find(manager, view);
            find.process("find|" + table);
        } else {
            view.write("Exit to the main menu");
        }

    }

    private void createQuery() {
        if (!exitMain) {
            createTableName();
        }
        if (!exitMain) {
            createColumnPrimaryKey();
        }
        if (!exitMain) {
            createColumn();
        }
    }

    private void createTableName() {
        boolean exit = false;
        while (!exit) {
            view.write("Enter a name for the table" + fromLetter + orExit);
            String input = view.read();

            if (input.equals("0")) {
                exit = true;
                exitMain = true;
            } else if (input.equals("")) {
                view.write("You must enter a name for the table, and you enter an empty string");
            } else {
                if (checkNameStartWithLetterB(input)) {
                    query += input + "(";
                    view.write("Name the new database: " + input);
                    exit = true;
                }
            }
        }
    }

    private void createColumnPrimaryKey() {
        boolean exit = false;
        while (!exit) {
            view.write("Enter a name for the column PRIMARY KEY" + fromLetter + orExit);
            String input = view.read();
            if (input.equals("0")) {
                exit = true;
                exitMain = true;
            } else if (input.equals("")) {
                view.write("You must enter a name for the column PRIMARY KEY, and you enter an empty string");
            } else {
                if (checkNameStartWithLetterB(input)) {
                    view.write("Column name PRIMARY KEY: " + input);
                    query += input + " SERIAL NOT NULL PRIMARY KEY";
                    exit = true;
                }
            }
        }
    }

    private void createColumn() {
        boolean exit = false;
        while (!exit) {
            view.write("Enter the name of another column" + fromLetter
                    + " or '5' to create a database with the introduction column" + orExit);
            String input = view.read();
            if (input.equals("5")) {
                query += ")";
                exit = true;
            } else if (input.equals("0")) {
                exit = true;
                exitMain = true;
            } else if (input.equals("")) {
                view.write("You must enter a name for the column, and you enter an empty string");
            } else {
                if (checkNameStartWithLetterB(input)) {
                    query += "," + input + " varchar(225)";
                    view.write("Name for another column: " + input);
                    createColumn();
                    exit = true;
                }
            }
        }
    }

    public boolean checkNameStartWithLetterB(String input) {
        char fistChar = input.charAt(0);
        if (!(fistChar >= 'a' && fistChar <= 'z') && !(fistChar >= 'A' && fistChar <= 'Z')) {
            view.write(String.format("Name must start with a letter, and you start with '%s'", fistChar));
            return false;
        }
        return true;
    }

    @Override
    public String commandFormat() {
        return "createTable";
    }

    @Override
    public String description() {
        return "to create a new table in steps";
    }
}
