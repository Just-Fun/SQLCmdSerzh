package ua.com.juja.serzh.sqlcmd.controller;

import org.reflections.Reflections;
import ua.com.juja.serzh.sqlcmd.controller.command.*;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by indigo on 25.08.2015.
 */
public class MainController {

    private List<Command> commands;
    private View view;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;

        // How to get all classes using Reflections!
        /*Reflections reflections = new Reflections(Command.class.getPackage().getName());
        Set<Class<? extends Command>> allCommands = reflections.getSubTypesOf(Command.class);
        commands.add(new IsConnected(manager, view));
        allCommands.stream()
                .filter(command -> !command.equals(Unsupported.class) && !command.equals(IsConnected.class))
                .forEach(command -> {
                    try {
                        commands.add(command.getConstructor(DatabaseManager.class, View.class).newInstance(manager, view));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        commands.add(new Unsupported(manager, view));*/

        commands = new ArrayList<>(Arrays.asList(
                new Connect(manager, view),
                new Help(manager, view),
                new Exit(manager, view),
                new IsConnected(manager, view),
                new Databases(manager, view),
                new Tables(manager, view),
                new Clear(manager, view),
                new CreateDatabase(manager, view),
                new DropDatabase(manager, view),
                new CreateTableSimple(manager, view),
                new CreateTable(manager, view),
                new DropTable(manager, view),
                new TableSize(manager, view),
                new InsertSimple(manager, view),
                new Insert(manager, view),
                new Find(manager, view),
                new Unsupported(manager, view)
        ));
    }

    public void run() {
        try {
            doWork();
        } catch (ExitException e) {
            // do nothing
        }
    }

    private void doWork() {
        view.write("Введите имя базы данных, с которой будем работать, имя пользователя и пароль в формате: " +
                "connect|database|userName|password");

        while (true) {
            String input = view.read();
            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                    printError(e);
                    break;
                }
            }
            view.write("Введи команду (или help для помощи):");
        }
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + cause.getMessage();
        }
        view.write("Неудача! по причине: " + message);
    }
}
