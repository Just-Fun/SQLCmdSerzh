package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.model.PostgresManager;
import ua.com.juja.serzh.sqlcmd.view.Console;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by serzh on 6/6/16.
 */
public class CreateTableSimpleTest {

    private Command command;
    private View view;
    private DatabaseManager manager;

    @Before
    public void setup() {
        manager = mock(PostgresManager.class);
        view = mock(Console.class);
        command = new CreateTableSimple(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("createTable");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("createTable34");
        assertFalse(canProcess);
    }

    @Test
    public void testExit() throws Exception {
        when(view.read()).thenReturn("0");
        command.process("createTable");

        verify(view).write(("Exit to the main menu"));
    }

    @Test
    public void testCreateOnlyIdColumn() throws Exception {
        when(view.read()).thenReturn("users5")
                .thenReturn("id")
                .thenReturn("5");
        command.process("createTable");

        verify(view).write(("Enter a name for the table(name must start with a letter) or '0' to exit to the main menu"));
        verify(view).write(("Name the new database: users5"));
        verify(view).write(("Enter a name for the column PRIMARY KEY(name must start with a letter) or '0' to exit to the main menu"));
        verify(view).write(("Column name PRIMARY KEY: id"));
        verify(view).write(("Enter the name of another column(name must start with a letter) or '5' to create a database with the introduction column or '0' to exit to the main menu"));
        verify(view).write(("Table users5 was successfully created."));
    }
}