package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Created by serzh on 6/3/16.
 */
public class DatabasesTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Databases(manager, view);
    }

    @Test
    public void testPrintGetTableNames() {
        when(manager.getDatabases()).thenReturn(new HashSet<>(Arrays.asList("db1", "db2")));
        command.process("databases");
        shouldPrint("[Existing databases: db1, db2]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }

    @Test
    public void canProcessListWithRightParameter() {
        boolean canProcess = command.canProcess("databases");
        assertTrue(canProcess);
    }

    @Test
    public void canProcessListWithWrongParameter() {
        boolean canNotProcess = command.canProcess("ghkk");
        assertFalse(canNotProcess);
    }

    @Test
    public void canProcessListWithoutParameter() {
        boolean canNotProcess = command.canProcess("");
        assertFalse(canNotProcess);
    }

    @Test
    public void testPrintEmptyTableData() {
        when(manager.getTableNames()).thenReturn(new HashSet<>());
        command.process(("databases"));
        shouldPrint("[Databases are absent]");
    }
}