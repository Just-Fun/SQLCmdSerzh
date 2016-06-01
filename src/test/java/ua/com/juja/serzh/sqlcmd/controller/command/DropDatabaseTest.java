package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        boolean canProcess = command.canProcess("dropDB|db");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canNotProcess = command.canProcess("dropFB|db");
        assertFalse(canNotProcess);
    }

    @Test
    public void testCanProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("dropDB");
        assertTrue(canProcess);
    }


    @Test
    public void testProcess() throws Exception {
        when(view.read()).thenReturn("y");
        command.process("dropDB|db");

        verify(view).write(String.format("Вы уверены, что хотите удалить db? Y/N"));
        verify(manager).dropDatabase("db");
        verify(view).write("Database 'db' была успешно удалена.");
    }

    @Test
    public void testProcessUpperY() throws Exception {
        when(view.read()).thenReturn("Y");
        command.process("dropDB|db");

        verify(view).write(String.format("Вы уверены, что хотите удалить db? Y/N"));
        verify(manager).dropDatabase("db");
        verify(view).write("Database 'db' была успешно удалена.");
    }

    @Test
    public void testActionCanceled() throws Exception {
        when(view.read()).thenReturn("N");
        command.process("dropDB|db");

        verify(view).write("Вы уверены, что хотите удалить db? Y/N");
        verify(view).write("Действие отменено!");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("dropDB|db|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'dropDB|databaseName', а ты ввел: dropDB|db|wrong", e.getMessage());
        }
    }
}