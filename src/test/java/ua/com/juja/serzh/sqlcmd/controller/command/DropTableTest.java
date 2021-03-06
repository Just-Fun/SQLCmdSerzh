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
public class DropTableTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new DropTable(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("dropTable|");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canNotProcess = command.canProcess("dropTables|");
        assertFalse(canNotProcess);
    }

    @Test
    public void testCanProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("dropTable");
        assertTrue(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        when(view.read()).thenReturn("y");
        command.process("dropTable|nameTable");
        verify(view).write("Are you sure you want to delete nameTable? Y/N");
        verify(manager).dropTable("nameTable");
        verify(view).write("Table nameTable has been successfully removed.");
    }

    @Test
    public void testActionCanceled() throws Exception {
        when(view.read()).thenReturn("n");
        command.process("dropTable|nameTable");
        verify(view).write("Are you sure you want to delete nameTable? Y/N");
        verify(view).write("Action canceled!");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("createTable|tableName|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The command format is 'dropTable|tableName', but you typed: createTable|tableName|wrong", e.getMessage());
        }
    }
}