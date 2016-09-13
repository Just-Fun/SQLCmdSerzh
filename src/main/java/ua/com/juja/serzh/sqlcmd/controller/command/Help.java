package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by serzh on 5/11/16.
 */
public class Help extends Command {

//    private View view;
    private List<Command> commands;

    public Help(DatabaseManager manager, View view) {
        super(manager, view);
        commands = new ArrayList<>(Arrays.asList(
                new Connect(manager, view),
                new Databases(manager, view),
                new Tables(manager, view),
                new CreateDatabase(manager, view),
                new DropDatabase(manager, view),
                new CreateTableSimple(manager, view),
                new CreateTable(manager, view),
                new TableSize(manager, view),
                new Clear(manager, view),
                new DropTable(manager, view),
                new InsertSimple(manager, view),
                new Insert(manager, view),
                new Find(manager, view),
                this,
                new Exit(manager, view)
        ));
    }

    @Override
    public void process(String input) {
        view.write("Current commands:");

        commands.forEach(command -> {
                    view.write("\t" + command.commandFormat());
                    view.write("\t\t" + command.description());
                }
        );
    }

    @Override
    public String commandFormat() {
        return "help";
    }

    @Override
    public String description() {
        return "to display that list on the screen";
    }
}
