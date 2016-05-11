package sqlcmd.controller.command;

import sqlcmd.view.View;

/**
 * Created by indigo on 28.08.2015.
 */
public class Unsupported implements Command {

    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("Несуществующая команда: " + command);
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String format() {
        return null;
    }
}
