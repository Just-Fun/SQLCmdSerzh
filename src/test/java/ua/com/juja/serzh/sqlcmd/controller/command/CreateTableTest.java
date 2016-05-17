package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 13.05.16.
 */
public class CreateTableTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new CreateTable(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("createTable|user");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("createTable34|user");
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("createTable");
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("createTable|user");
        verify(manager).createTable("user");
        verify(view).write("Таблица user была успешно создана.");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("createTable|tableName|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'createTable|tableName', а ты ввел: createTable|tableName|wrong", e.getMessage());
        }
    }
}