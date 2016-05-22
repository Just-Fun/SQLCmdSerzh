package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 13.05.16.
 */
public class DropDatabaseTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DropDatabase(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess(new UserInput("dropDB|db"));
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canNotProcess = command.canProcess(new UserInput("dropFB|db"));
        assertFalse(canNotProcess);
    }

    @Test
    public void testCanProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess(new UserInput("dropDB"));
        assertTrue(canProcess);
    }


    @Test
    public void testProcess() throws Exception {
        command.process(new UserInput("dropDB|db"));
        verify(manager).dropDatabase("db");
        verify(view).write("Database db была успешно удалена.");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process(new UserInput("dropDB|db|wrong"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'dropDB|databaseName', а ты ввел: dropDB|db|wrong", e.getMessage());
        }
    }
}