package sqlcmd.controller.command;

import sqlcmd.model.DatabaseManager;
import sqlcmd.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by indigo on 28.08.2015.
 */
public class Help implements Command {

    private DatabaseManager manager;
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
                new Insert(manager, view),
                new Find(manager, view),
                this,
                new sqlcmd.controller.command.List(manager, view),
                new Exit(view)
        ));
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String userCommand) {
        view.write("Существующие команды:");

        for (Command command : commands) {
            view.write("\t" + command.format());
            view.write("\t\t" + command.description());
        }
//
//        view.write("\tconnect|databaseName|userName|password");
//        view.write("\t\tдля подключения к базе данных, с которой будем работать");

//        view.write("\tlist");
//        view.write("\t\tдля получения списка всех таблиц базы, к которой подключились");

//        view.write("\tclear|tableName");
//        view.write("\t\tдля очистки всей таблицы"); // TODO а если юзер случайно ввел команду? Может переспросить его?

//        view.write("\tinsert|tableName|column1|value1|column2|value2|...|columnN|valueN");
//        view.write("\t\tдля создания записи в таблице");
//
//        view.write("\tfind|tableName");
//        view.write("\t\tдля получения содержимого таблицы 'tableName'");

//        view.write("\thelp");
//        view.write("\t\tдля вывода этого списка на экран");

//        view.write("\texit");
//        view.write("\t\tдля выхода из программы");
    }

    @Override
    public String description() {
        return "для вывода этого списка на экран";
    }

    @Override
    public String format() {
        return "help";
    }
}
