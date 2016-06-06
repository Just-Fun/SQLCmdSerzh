package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 6/6/16.
 */
public class CreateTableParts extends Command {

    protected DatabaseManager manager;
    protected View view;

    public CreateTableParts(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public void process(String input) {
        validationParameters(input);
        createExtraMenu();
        String command = "";
        manager.createTable(command);

        String tableName = "";
        view.write(String.format("Таблица %s была успешно создана.", tableName));
        Command find = new Find(manager, view);
        find.process("find|" + tableName); // красивый вывод новосозданной таблички
    }

    private void createExtraMenu() {
        //id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225)
        view.write("введите:\n1 - для создания колонки PRIMARY KEY\n2 - для создания колонки varchar" +
                "\n3 - для создания таблицы из введенных вами данных\n4 - для выхода из подменю в основное меню");
        String input = view.read();
        checkAction(input);
    }

    private String checkAction(String input) {
        String result = "";
        boolean exit = false;
        while (!exit) {
            if (input.equals("1")) {
                view.write("Введите имя для колонки PRIMARY KEY");
                input = view.read();
                result += input + "SERIAL NOT NULL PRIMARY KEY";
            } else if (input.equals("2")) {
                view.write("Введите имя для колонки");
                input = view.read();
                result += input + "varchar(225)";
            } else if (input.equals("3")) {
                checkResult(result);
                return result;
            } else if (input.equals("4")) {
                view.write("выход выход из подменю");
                exit = true;
            } else {
                view.write("нужно ввести:\n1 - для создания колонки PRIMARY KEY\n2 - для создания колонки varchar\n" +
                        "3 - для выхода из подменю в основное меню, \nа вы ввели: " + input +
                        "Введите один из предложенных вариантов");
                input = view.read();
            }
        }
        return result;
    }

    private void checkResult(String result) {

    }

    @Override
    public String commandFormat() {
        return "createTableStep";
    }

    @Override
    public String description() {
        return "для создания новой таблицы пошагово";
    }
}
