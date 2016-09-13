package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    public void testCanProcessWithoutParametersString() {
        boolean canProcess = command.canProcess("dropDB");
        assertTrue(canProcess);
    }


    @Test
    public void testProcessLowerYes() throws Exception {
        when(view.read()).thenReturn("y");
        command.process("dropDB|db");

        verify(view).write("Are you sure you want to delete db? Y/N");
        verify(manager).dropDatabase("db");
        verify(view).write("Database 'db' has been successfully removed.");
    }

    @Test
    public void testProcessUpperYes() throws Exception {
        when(view.read()).thenReturn("Y");
        command.process("dropDB|db");

        verify(view).write("Are you sure you want to delete db? Y/N");
        verify(manager).dropDatabase("db");
        verify(view).write("Database 'db' has been successfully removed.");
    }

    @Test
    public void testActionCanceled() throws Exception {
        when(view.read()).thenReturn("N");
        command.process("dropDB|db");

        verify(view).write("Are you sure you want to delete db? Y/N");
        verify(view).write("Action canceled!");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("dropDB|db|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The command format is 'dropDB|databaseName', but you typed: dropDB|db|wrong", e.getMessage());
        }
    }

    @Test
    public void testDropConnectedDatabase() throws Exception {
        when(view.read()).thenReturn("Y");
        when(manager.getDatabaseName()).thenReturn("currentDB");
        command.process("dropDB|currentDB");
        verify(view).write("Are you sure you want to delete currentDB? Y/N");
        verify(view).write("You can not delete the base to which you are connected.");
        verify(view).write("To delete the current database 'currentDB', connect to another database.");
    }

    @Test(expected = RuntimeException.class)
    public void testProcessSQLException() throws SQLException {
        when(view.read()).thenReturn("Y");
        command.process("dropDB|db");
        verify(view).write("Are you sure you want to delete db? Y/N");
        doThrow(new SQLException()).when(manager).dropDatabase("db");
    }
}