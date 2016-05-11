package sqlcmd.controller.command;

import sqlcmd.view.View;

/**
 * Created by indigo on 28.08.2015.
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
    public void process(String command) {
        view.write("До скорой встречи!");
        throw new ExitException();
    }

    @Override
    public String description() {
        return "для выхода из программы";
    }

    @Override
    public String format() {
        return "exit";
    }
}
