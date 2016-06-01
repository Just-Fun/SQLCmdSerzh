package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 13.05.16.
 */
public class CreateDatabaseTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateDatabase(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("createDB|databaseName");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("createDB34|databaseName");
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessWithoutParameters() throws Exception {
        boolean canProcess = command.canProcess("createDB");
        assertTrue(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("createDB|databaseName");
        verify(manager).createDatabase("databaseName");
        verify(view).write("Database databaseName была успешно создана.");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("createDB|databaseName|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'createDB|databaseName', а ты ввел: createDB|databaseName|wrong", e.getMessage());
        }
    }

    @Test
    public void testNameStartWithNumber() throws Exception {
        try {
            command.process("createDB|12databaseName");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Имя базы должно начинаться с буквы, а у тебя начинается с '1'", e.getMessage());
        }
    }
}