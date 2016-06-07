package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by serzh on 6/7/16.
 */
public class InsertSimpleTest {

    private Command command;

    @Before
    public void setup() {
        DatabaseManager manager = mock(DatabaseManager.class);
        View view = mock(View.class);
        command = new InsertSimple(manager, view);
    }

    @Test
    public void testCanProcess() throws Exception {
        boolean canProcess = command.canProcess("insertTable|tableName");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessWithoutParameters() throws Exception {
        boolean canProcess = command.canProcess("insertTable|tableName");
        assertTrue(canProcess);
    }

    @Test
    public void testProcessWithWrongCommand() throws Exception {
        boolean canProcess = command.canProcess("insertTable34");
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessFindWithOnlySlash() {
        try {
            command.process("insertTable|");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'insertTable|tableName', а ты ввел: insertTable|", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        try {
            command.process("insertTable|tableName|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'insertTable|tableName', а ты ввел: insertTable|tableName|qwe", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        try {
            command.process("insertTable");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'insertTable|tableName', а ты ввел: insertTable", e.getMessage());
        }
    }
}