package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 19.05.16.
 */
public class IsConnectedTest {
    DatabaseManager manager;
    View view;
    Command command;

    @Before
    public void setup() {
        manager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new IsConnected(manager, view);
    }

    @Test
    public void testProcess() throws Exception {
        command.process(new UserInput("if does not connected"));
        verify(view).write("Вы не можете пользоваться командой 'if does not connected' пока не подключитесь с помощью комманды connect|databaseName|userName|password");
    }
}