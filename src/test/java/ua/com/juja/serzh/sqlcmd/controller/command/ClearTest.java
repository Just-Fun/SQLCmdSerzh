package ua.com.juja.serzh.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.serzh.sqlcmd.controller.util.UserInput;
import ua.com.juja.serzh.sqlcmd.model.DatabaseManager;
import ua.com.juja.serzh.sqlcmd.view.View;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by serzh on 5/11/16.
 */
public class ClearTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager, view);
    }

    @Test
    public void testClearTable() {
        command.process(new UserInput("clear|user"));

        verify(manager).clear("user");
        verify(view).write("Таблица user была успешно очищена.");
    }

    @Test
    public void testCanProcessClearWithParametersString() {
        boolean canProcess = command.canProcess(new UserInput("clear|user"));
        assertTrue(canProcess);
    }

    @Test
    public void testCantProcessClearWithoutParametersString() {
        boolean canProcess = command.canProcess(new UserInput("clear"));
        assertTrue(canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersIsLessThan2() {
        try {
            command.process(new UserInput("clear"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'clear|tableName', а ты ввел: clear", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersIsMoreThan2() {
        try {
            command.process(new UserInput("clear|table|qwe"));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Формат команды 'clear|tableName', а ты ввел: clear|table|qwe", e.getMessage());
        }
    }
}
