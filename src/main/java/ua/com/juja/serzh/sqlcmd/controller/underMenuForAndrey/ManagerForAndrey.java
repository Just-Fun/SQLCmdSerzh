package ua.com.juja.serzh.sqlcmd.controller.underMenuForAndrey;

import ua.com.juja.serzh.sqlcmd.controller.command.Command;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 03.06.16.
 */
public class ManagerForAndrey extends Command {

    protected DatabaseManager manager;
    protected View view;

    public ManagerForAndrey(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public void process(String input) {
        validationParameters(input);
        createExtraMenu();
    }

    private void createExtraMenu() {
        view.write("введите:\n1 - для фейерверка\n2 - для душа\n3 - для выхода из подменю");
        String input = view.read();
        checkAction(input);
    }

    private void checkAction(String input) {
        boolean exit = false;
        while (!exit) {
            if (input.equals("1")) {
                view.write("фейерверк");
                new SomeLogik(view).fire();
                input = view.read();
            } else if (input.equals("2")) {
                view.write("душ");
                input = view.read();
            } else if (input.equals("3")) {
                view.write("выход выход из подменю");
                exit = true;
            } else {
                view.write("нужно ввести:\n1 - для фейерверка\n2 - для душа\n3 - для выхода, \nа вы ввели: " + input);
                input = view.read();
            }
        }
    }


    @Override
    public String commandFormat() {
        return "extra";
    }

    @Override
    public String description() {
        return "для входа в дополнительное меню.";
    }
}