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
        boolean canProcess = command.canProcess("createTableSQL|user");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("createTable34|user");
        assertFalse(canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess("createTableSQL");
        assertTrue(canProcess);
    }

    @Test
    public void testProcess() throws Exception {
        command.process("createTableSQL|user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        verify(manager).createTable("user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE, password varchar(225))");
        verify(view).write("Table user was successfully created.");
    }

    @Test
    public void testProcessWithoutParameters() throws Exception {
        try {
            command.process("createTableSQL|user");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The command format is 'createTableSQL|tableName(column1,column2,...,columnN)' in SQL !!! format, but you typed: createTableSQL|user", e.getMessage());
        }
    }

    @Test
    public void testProcessWrongFormat() throws Exception {
        try {
            command.process("createTable|tableName|wrong");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("The command format is 'createTableSQL|tableName(column1,column2,...,columnN)', but you typed: createTable|tableName|wrong", e.getMessage());
        }
    }
}