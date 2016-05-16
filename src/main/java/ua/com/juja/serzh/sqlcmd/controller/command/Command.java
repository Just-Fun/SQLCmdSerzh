package ua.com.juja.serzh.sqlcmd.controller.command;

/**
 * Created by serzh on 5/11/16.
 */
public interface Command {

    boolean canProcess(String command);

    void process(String command);

    public String description();

    public String format();
}
