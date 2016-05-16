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
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("dropTable");
        assertFalse(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("dropTable|nameTable");
        verify(manager).dropTable("nameTable");
        verify(view).write("Таблица nameTable была успешно удалена.");
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("createTable|tableName|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'create|dropTable', а ты ввел: createTable|tableName|wrong", e.getMessage());
        }
    }
}