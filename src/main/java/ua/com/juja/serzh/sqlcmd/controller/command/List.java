package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by serzh on 5/11/16.
 */
public class List implements Command {

    private DatabaseManager manager;
    private View view;

    public List(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        Set<String> tableNames = manager.getTableNames();
//        String message = Arrays.toString(tableNames);
//        view.write(message);
        view.write(tableNames.toString());
    }

    @Override
    public String description() {
        return "для получения списка всех таблиц базы, к которой подключились";
    }

    @Override
    public String commandFormat() {
        return "list";
    }
}
