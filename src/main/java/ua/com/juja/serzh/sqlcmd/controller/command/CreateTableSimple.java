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

    String orExit = " или '0' для выхода в основное меню";
    String fromLetter = "(имя должно начинаться с буквы)";
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
            view.write(String.format("Таблица %s была успешно создана.", table));
            Command find = new Find(manager, view);
            find.process("find|" + table); // красивый вывод новосозданной таблички
        } else {
            view.write("Выход в основное меню");
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
            view.write("Введите имя для создаваемой таблицы" + fromLetter + orExit);
            String input = view.read();

            if (input.equals("0")) {
                exit = true;
                exitMain = true;
            } else if (input.equals("")) {
                view.write("Нужно ввести имя для создаваемой таблицы, а вы ввели пустую строку");
            } else {
                if (checkNameStartWithLetterB(input)) {
                    query += input + "(";
                    view.write("Имя новой базы: " + input);
                    exit = true;
                }
            }
        }
    }

    private void createColumnPrimaryKey() {
        boolean exit = false;
        while (!exit) {
            view.write("Введите имя для колонки PRIMARY KEY" + fromLetter + orExit);
            String input = view.read();
            if (input.equals("0")) {
                exit = true;
                exitMain = true;
            } else if (input.equals("")) {
                view.write("Нужно ввести имя для колонки PRIMARY KEY, а вы ввели пустую строку");
            } else {
                if (checkNameStartWithLetterB(input)) {
                    view.write("Имя колонки PRIMARY KEY: " + input);
                    query += input + " SERIAL NOT NULL PRIMARY KEY";
                    exit = true;
                }
            }
        }
    }

    private void createColumn() {
        boolean exit = false;
        while (!exit) {
            view.write("Введите имя для еще одной колонки" + fromLetter + " или '5' для создания базы с введенными колонками" + orExit);
            String input = view.read();
            if (input.equals("5")) {
                query += ")";
                exit = true;
            } else if (input.equals("0")) {
                exit = true;
                exitMain = true;
            } else if (input.equals("")) {
                view.write("Нужно ввести имя для колонки, а вы ввели пустую строку");
            } else {
                if (checkNameStartWithLetterB(input)) {
                    query += "," + input + " varchar(225)";
                    view.write("Имя еще одной колонки: " + input);
                    createColumn();
                    exit = true;
                }
            }
        }
    }

    public boolean checkNameStartWithLetterB(String input) {
        char fistChar = input.charAt(0);
        if (!(fistChar >= 'a' && fistChar <= 'z') && !(fistChar >= 'A' && fistChar <= 'Z')) {
            view.write(String.format("Имя должно начинаться с буквы, а у тебя начинается с '%s'", fistChar));
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
        return "для создания новой таблицы пошагово";
    }
}
