package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(UserInput command) {
        view.write("До скорой встречи!");
        throw new ExitException();
    }

    @Override
    public String description() {
        return "для выхода из программы";
    }

    @Override
    public String commandFormat() {
        return "exit";
    }
}
