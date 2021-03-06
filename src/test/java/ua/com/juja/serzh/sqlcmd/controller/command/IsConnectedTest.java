package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.assertEquals;
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
        command.process("if does not connected");
        verify(view).write("You can not use the commands until you connect using command connect|databaseName|userName|password");
    }

    @Test
    public void testDescription() throws Exception {
        String description = command.description();
        assertEquals(null, description);
    }

    @Test
    public void testCommandFormat() throws Exception {
        String description = command.commandFormat();
        assertEquals(null, description);
    }
}