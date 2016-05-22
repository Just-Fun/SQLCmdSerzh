package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by serzh on 5/11/16.
 */
public class Help extends Command {

    private View view;
    private List<Command> commands;

    public Help(View view) {
        this.view = view;
        commands = new ArrayList<>(Arrays.asList(
                new Connect(manager, view),
                new Clear(manager, view),
                new CreateDatabase(manager, view),
                new DropDatabase(manager, view),
                new CreateTable(manager, view),
                new DropTable(manager, view),
                new Insert(manager, view),
                new Find(manager, view),
                this,
                new Tables(manager, view),
                new Exit(view)
        ));
    }

    @Override
    public void process(UserInput input) {
        view.write("Существующие команды:");

        for (Command command : commands) {
            view.write("\t" + command.commandFormat());
            view.write("\t\t" + command.description());
        }
    }
// TODO проверить, что за для вывода этого!!! списка
    @Override
    public String description() {
        return "для вывода этого списка на экран";
    }

    @Override
    public String commandFormat() {
        return "help";
    }
}
