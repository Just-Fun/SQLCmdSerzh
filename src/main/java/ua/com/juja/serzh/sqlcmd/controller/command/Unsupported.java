package ua.com.juja.serzh.sqlcmd.controller.command;

import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.view.View;

/**
 * Created by serzh on 5/11/16.
 */
public class Unsupported extends CommandAbstract {

    public Unsupported(View view) {
        super(view);
    }

//    @Override
//    public boolean canProcess(String command) {
//        return true;
//    }


    @Override
    public boolean canProcess(UserInput command) {
        return true;
    }

    @Override
    public void process(UserInput input) {
        view.write("Несуществующая команда: " + input.toString());
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public String commandFormat() {
        return null;
    }
}
