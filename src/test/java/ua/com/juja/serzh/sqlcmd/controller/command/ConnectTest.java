package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 19.05.16.
 */
public class ConnectTest {
    DatabaseManager manager;
    View view;
    Command command;

    @Before
    public void setup() {
        manager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new Connect(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("connect|");
        assertTrue(canProcess);
    }

    @Test
    public void testCanNotProcess() throws Exception {
        boolean canProcess = command.canProcess("conneeeect|");
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("connect|databaseName|userName|password");
        verify(view).write("Успех!");
    }

    @Test
    public void testProcessWithWrongParameters() throws Exception {
        try {
            command.process("connect|databaseName|userName");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'connect|databaseName|userName|password', а ты ввел: connect|databaseName|userName", e.getMessage());
        }
    }
}