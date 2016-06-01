package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Exit extends Command {

    public Exit(View view) {
        super(view);
    }

    @Override
    public void process(String command) {
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
